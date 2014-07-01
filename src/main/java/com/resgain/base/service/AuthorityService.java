package com.resgain.base.service;

import java.util.ArrayList;
import java.util.List;

import jodd.util.StringUtil;

import org.springframework.stereotype.Service;

import com.resgain.base.abs.AbstractService;
import com.resgain.base.abs.IDictService;
import com.resgain.base.entity.AuthGroup;
import com.resgain.base.entity.Authority;
import com.resgain.base.entity.DictCode;
import com.resgain.base.extjs.TreeNode;

@Service
public class AuthorityService extends AbstractService implements IDictService
{
	public void saveAuthGroup(AuthGroup ag) {
		super.save(ag);
	}

	public void insertAuthority(Authority auth) {
		getSession().save(auth);
	}

	public void sss()
	{
		throw new RuntimeException();
	}

	public List<TreeNode> getAuthorities(String id) {
		List<TreeNode> ret = new ArrayList<TreeNode>();
		if (StringUtil.isBlank(id)) {
			List<AuthGroup> agList = find(AuthGroup.class, null, null);
			for (AuthGroup ag : agList) {
				ret.add(new TreeNode(ag.getId(), ag.getName(), "", false, false, false));
			}
		} else {
			List<Authority> authList = find(Authority.class, null, "a.agId=?", id);
			for (Authority auth : authList) {
				ret.add(new TreeNode(auth.getId(), auth.getName(), "", false, false, true));
			}
		}
		return ret;
	}

	@Override
	public List<DictCode> getDataList(String category, String pcode, String keyword) {
		List<DictCode> ret = new ArrayList<DictCode>();
		List<Authority> authList = find(Authority.class, null, null);
		for (Authority auth : authList) {
			ret.add(new DictCode(category, auth.getCode(), null, auth.getName()));
		}
		return ret;
	}
}
