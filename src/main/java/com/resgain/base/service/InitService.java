package com.resgain.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.resgain.base.abs.AbstractService;
import com.resgain.base.entity.AuthGroup;
import com.resgain.base.entity.Authority;
import com.resgain.base.entity.Dept;
import com.resgain.base.entity.Role;
import com.resgain.base.entity.User;

/**
 * 系统数据初始化服务类
 * @author gyl
 */
@Service
public class InitService extends AbstractService
{
	private static final String CATEGORY = "SYSTEM";
	private static final String KEY = "version";
	public static final long VERSION = 1;

	@Autowired
	private ConfigService configService;

	@Autowired
	private DeptService deptService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthorityService authorityService;

	/**
	 * 根据系统版本进行数据初始化
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void initData() {
		long cv = configService.getLongValue(CATEGORY, KEY);
		if (cv < VERSION) {
			AuthGroup ag = new AuthGroup(null, "基础设置管理");
			authorityService.saveAuthGroup(ag);
			authorityService.insertAuthority(new Authority("USER_MANAGE", "员工管理", ag.getId()));
			authorityService.insertAuthority(new Authority("ROLE_MANAGE", "角色管理", ag.getId()));
			authorityService.insertAuthority(new Authority("DEPT_MANAGE", "部门管理", ag.getId()));
			authorityService.insertAuthority(new Authority("DICT_MANAGE", "字典管理", ag.getId()));
			authorityService.insertAuthority(new Authority("MENU_MANAGE", "菜单设置", ag.getId()));


			Dept dept = new Dept(null, "XXX公司");
			deptService.saveDept(dept);
			Role role = new Role("系统管理员");
			roleService.saveRole(role);
			//roleService.saveRoleAuth(role.getId(), authIds);
			User user = new User(dept.getId(), "admin", "admin", "系统管理员");
			userService.saveUser(user);
			userService.saveUserRole(user.getId(), role.getId());
			configService.setValue(CATEGORY, KEY, String.valueOf(VERSION));
		}
	}
}
