奖助学金申请功能开发

# 1. 本章任务

本章需要开发的奖助学金申请功能，是整个系统的核心功能，用于实现学生角色发起奖助学金项目的申请。

整体的申请流程是学生申请--班主任审核--学院管理员审核--学校管理员审核，全部审核通过后即为申请成功。

如果某一流程审批被驳回，即为失败。

# 2. 数据库表结构分析

审批过程中相关数据使用flow表来记录，flow表结构如下：
```sql
CREATE TABLE `flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `studentId` int(11) DEFAULT NULL,
  `studentName` varchar(255) DEFAULT NULL,
  `projectId` int(11) DEFAULT NULL,
  `projectName` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `classUserId` int(11) DEFAULT NULL,
  `classAdvice` varchar(255) DEFAULT NULL,
  `collegeUserId` int(11) DEFAULT NULL,
  `collegeAdvice` varchar(255) DEFAULT NULL,
  `schoolUserId` int(11) DEFAULT NULL,
  `schoolAdvice` varchar(255) DEFAULT NULL,
  `currentUserId` int(11) DEFAULT NULL,
  `currentNode` varchar(255) DEFAULT NULL COMMENT 'class/college/shcool/success/fail',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
```
这个表结构很重要，我们来详细解释下。

 1. id是记录的唯一编号，从1开始自动增长。
 2. studentId/studentName是提交申请的学生编号、姓名
 3. projectId/projectName是学生申请的奖助学金项目的编号、名称
 4. content是学生填写的申请内容
 5. classUserId/classAdvice是班主任编号、班主任审核意见
 6. collegeUserId/collegeAdvice是学院管理员编号、学院管理员审核意见
 7. schoolUserId/schoolAdvice是学校管理员编号、学校管理员审核意见
 8. currentUserId是当前处理节点的用户id，例如待班主任申请的话，currentUserId即为班主任的id
 9. currentNode是当前审核节点，class/college/shcool值表示班主任审核中/学院审核中/学校审核中,success表示全部审核通过，fail表示被驳回。

好的，知道了存储结构后，学生奖助学金申请的需求基本也就清楚了。

学生需要能够查看已申请的记录相关的信息，也可以发起新的项目申请。

# 3. 学生查看申请记录功能开发

页面上显示申请记录的表格：
```html
		<table id="mainTable" title="已申请项目列表" class="easyui-datagrid" url="CoreServlet?method=getStudentApplyFlowPage"
		 pagination="true" singleSelect="true" fitColumns="true">
			<thead>
				<tr>
					<th data-options="field:'id',width:50">申请序号</th>
					<th data-options="field:'studentName',width:50">申请人</th>
					<th data-options="field:'projectName',width:50">申请项目名称</th>
					<th data-options="field:'content',width:100">申请说明</th>
					<th data-options="field:'classAdvice',width:100">班主任审核意见</th>
					<th data-options="field:'collegeAdvice',width:100">学院审核意见</th>
					<th data-options="field:'schoolAdvice',width:100">学校审核意见</th>
					<th data-options="field:'currentNode',width:100" formatter="formatCurrentNode">进度</th>
				</tr>
			</thead>
		</table>
```
其中进度需要格式化:
```js
			// 格式化
			function formatCurrentNode(val, row) {
				if (val == "class") {
					return "待班主任审核";
				} else if (val == "college") {
					return "待学院审核";
				} else if (val == "shcool") {
					return "待学校审核";
				} else if (val == "success") {
					return "通过";
				} else if (val == "fail") {
					return "驳回";
				}
				return "";
			}
```
然后编写后台分页方法getStudentApplyFlowPage,修改CoreServlet:
```java
		// 获取学生申请流程列表
		else if (method.equals("getStudentApplyFlowPage")) {
			FlowDao flowDao = new FlowDao();
			total = flowDao.getCountByStudentId(loginUser.getId());
			result.setTotal(total);
			result.setRows(flowDao.getPageByStudentId(page, rows, loginUser.getId()));
		}
```
修改UserDao，增加分页查询方法：
```java
	/**
	 * 获取数量
	 */
	public int getCountByStudentId(String studentId) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select count(id) from flow where studentId=?";
		QueryRunner runner = new QueryRunner();
		Object[] params = { studentId };
		Number number = (Number) runner.query(conn, sql, new ScalarHandler(), params);
		int value = number.intValue();
		ConnectionUtils.releaseConnection(conn);
		return value;
	}

	/**
	 * 分页查询
	 */
	public List<Flow> getPageByStudentId(int page, int rows, String studentId) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from flow where studentId=? limit ?,?";
		QueryRunner runner = new QueryRunner();
		Object[] params = { studentId, (page - 1) * rows, rows };
		List<Flow> flows = runner.query(conn, sql, new BeanListHandler<Flow>(Flow.class), params);
		ConnectionUtils.releaseConnection(conn);
		return flows;
	}
```
注意学生只能查看自己的申请，所以查询条件里面要限制studentId。

# 4. 学生发起申请功能开发

学生发起申请，也是通过弹窗的方式实现，选择要申请的项目，然后填写申请理由，提交即可。

此处注意后台需要补全flow表需要的参数，例如班主任、学院管理员、学校管理员的id，就需要查询出来之后填入flow表。

