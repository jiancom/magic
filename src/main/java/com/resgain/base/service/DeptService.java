package com.resgain.base.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.resgain.base.abs.AbstractService;
import com.resgain.base.abs.IDictService;
import com.resgain.base.entity.Dept;
import com.resgain.base.entity.DictCode;

@Service
public class DeptService extends AbstractService implements IDictService
{
	// 获取部门列表
	// @Cacheable(value = "deptCache")
	public List<Dept> getDeptList() {
		Dept root = get(Dept.class, null, "a.pid is null");
		List<Dept> all = find(Dept.class, null, null);
		List<Dept> ret = new ArrayList<Dept>();
		for (Dept dept : all) {
			if (root.getId().equals(dept.getPid())) {
				ret.add(dept);
				setSub(dept, all);
			}
		}
		return ret;
	}

	// 获取部门信息
	@Cacheable(value = "deptCache")
	public Dept getDept(String id) {
		return get(Dept.class, id);
	}

	// 保存部门信息
	@CacheEvict(value = "deptCache", allEntries = true)
	public void saveDept(Dept dept) {
		if (isNullOrSpace(dept.getPid()))
			dept.setPid(null);
		smartSave(dept);
	}

	// 删除部门信息
	@CacheEvict(value = "deptCache", allEntries = true)
	public void delDept(String id) {
		delete(Dept.class, id);
	}

	@CacheEvict(value = "deptCache", allEntries = true)
	public void chanePid(String sid, String pid) {
		if (!isNullOrSpace(sid, pid)) {
			update(Dept.class, "pid=?", "id=?", pid, sid);
		}
	}

	@Cacheable(value = "deptCache")
	@Override
	public List<DictCode> getDataList(String category, String pcode, String keyword) {
		List<DictCode> ret = new ArrayList<DictCode>();
		List<Dept> tmp = find(Dept.class, null, "a.type!=2");
		for (Dept dept : tmp) {
			ret.add(new DictCode(category, dept.getId(), null, dept.getName()));
		}
		return ret;
	}

	private void setSub(Dept dept, List<Dept> dl) {
		for (Dept dept2 : dl) {
			if (dept.getId().equals(dept2.getPid())) {
				dept.getChildren().add(dept2);
				setSub(dept2, dl);
			}
		}
	}
}
