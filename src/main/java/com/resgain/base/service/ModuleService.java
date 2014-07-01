package com.resgain.base.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.resgain.base.abs.AbstractService;
import com.resgain.base.entity.Menu;
import com.resgain.base.entity.Module;
import com.resgain.base.util.SessionUtil;

/**
 * 模块以及菜单相关业务方法类
 * @author gyl
 */
@Service
public class ModuleService extends AbstractService
{
	// 取得所有模块信息
	public List<Module> getModuleList() {
		return find(Module.class, null, null);
	}

	// 保存模块信息
	public void saveModule(Module module) {
		super.save(module);
	}

	public void saveMenu(Menu menu) {
		super.save(menu);
	}

	public void modiMenuName(String id, String name) {
		Menu menu = get(Menu.class, id);
		if (menu != null && !isNullOrSpace(name))
			menu.setName(name);
	}

	public void delMenu(String id) {
		delete(Menu.class, id);
	}

	public Menu addMenu(String pid, String moduleId) {
		Module m = get(Module.class, moduleId);
		if (m != null) {
			Menu menu = new Menu(pid, m.getName(), m.getCode());
			saveMenu(menu);
			menu.setModule(m);
			return menu;
		}
		return null;
	}

	public List<Menu> getMenuList() {
		List<Menu> tmpList = find(Menu.class, "module", null);
		Collections.sort(tmpList, new Comparator<Menu>() {
			@Override
			public int compare(Menu o1, Menu o2) {
				return (o1.getSort() > o2.getSort())?1:-1;
			}
		});
		List<Menu> ret = new ArrayList<Menu>();
		for (Menu menu : tmpList) {
			if (isNullOrSpace(menu.getPid())) {
				setChild(menu, tmpList);
				if(!menu.getSubList().isEmpty())
					ret.add(menu);
			}
		}
		return ret;
	}

	private void setChild(Menu m, List<Menu> mlist) {
		for (Menu menu : mlist) {
			if (m.getId().equals(menu.getPid())) {
				if(SessionUtil.hasAuth(menu.getModule().getAuth())){
					m.getSubList().add(menu);
					setChild(menu, mlist);
				}
			}
		}
	}
}