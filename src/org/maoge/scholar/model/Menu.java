package org.maoge.scholar.model;

/**
 * �˵�
 */
public class Menu {

	private String id;
	private String name;
	private String path;

	/**
	 * �����ֶΣ����ڷ����Ƿ�ѡ����Ϣ
	 */
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
