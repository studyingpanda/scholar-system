package org.maoge.scholar.test;

import org.maoge.scholar.dao.MenuDao;
import org.maoge.scholar.model.Menu;

public class MenuTest {
	public static void main(String[] args) throws Exception {
		MenuDao menuDao = new MenuDao();
		Menu addMenu = new Menu();
		addMenu.setName("²Ëµ¥xxx");
		addMenu.setPath("/xxx");
		menuDao.insert(addMenu);

		Menu updateMenu = new Menu();
		updateMenu.setId("1");
		updateMenu.setName("²Ëµ¥yyy");
		updateMenu.setPath("/yyy");
		menuDao.update(updateMenu);

		Menu queryMenu = menuDao.getById("1");
		System.out.println(queryMenu.getName());

		System.out.println(menuDao.getAll().size());
		System.out.println(menuDao.getCount());
		System.out.println(menuDao.getPage(1, 10).size());

		menuDao.deleteById("2");
	}
}
