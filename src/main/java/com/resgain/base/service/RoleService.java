package com.resgain.base.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.resgain.base.abs.AbstractService;
import com.resgain.base.abs.IDictService;
import com.resgain.base.entity.Authority;
import com.resgain.base.entity.DictCode;
import com.resgain.base.entity.Role;
import com.resgain.base.entity.RoleAuth;

@Service
public class RoleService extends AbstractService implements IDictService
{

	public List<Role> getRoleList() {
		return find(Role.class, null, null);
	}

	public Role getRole(String id){
		return get(Role.class, id);
	}

	public void saveRole(Role role) {
		smartSave(role);
	}

	public void delRole(String id) {
		delete(Role.class, id);
	}

	@Override
	public List<DictCode> getDataList(String category, String pcode, String keyword) {
		List<DictCode> ret = new ArrayList<DictCode>();
		List<Role> tmp = find(Role.class, null, null);
		for (Role role : tmp) {
			ret.add(new DictCode(category, role.getId(), null, role.getName()));
		}
		return ret;
	}

	public List<Authority> getRoleAuths(String roleId) {
		return find(Authority.class, null, "a.id in (select b.authId from com.resgain.base.entity.RoleAuth b where b.roleId=?)", roleId);
	}

	public void saveRoleAuth(String roleId, String authIds) {
		if (isNullOrSpace(roleId, authIds))
			return;
		getSession().createQuery("delete from " + RoleAuth.class.getName() + " where roleId=?").setParameter(0, roleId).executeUpdate();
		for (String authId : authIds.split(",")) {
			if (isNullOrSpace(authId))
				continue;
			super.save(new RoleAuth(roleId, authId.trim()));
		}
	}

}
