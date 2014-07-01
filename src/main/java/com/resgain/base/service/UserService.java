package com.resgain.base.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.resgain.base.abs.AbstractService;
import com.resgain.base.abs.IDictService;
import com.resgain.base.entity.Authority;
import com.resgain.base.entity.DictCode;
import com.resgain.base.entity.User;
import com.resgain.base.entity.UserAuth;
import com.resgain.base.entity.UserRole;

@Service
public class UserService extends AbstractService implements IDictService
{
	public void saveUser(User user) {
		if (isNullOrSpace(user.getId())){
			//user.setPass(DigestUtils.md5DigestAsHex(user.getPass().getBytes()));
			user.setPass(Hashing.sha1().hashString(user.getPass(), Charsets.UTF_8).toString());
		} else {
			User e = get(User.class, user.getId());
			if (!e.getPass().equals(user.getPass()))
				user.setPass(Hashing.sha1().hashString(user.getPass(), Charsets.UTF_8).toString());
			getSession().evict(e);
		}
		super.smartSave(user);
	}

	public User getUser(String id){
		return get(User.class, id);
	}

	public void deleteUser(String id){
		delete(User.class, id);
	}

	public List<User> getAllUser(String storeId) {
		return find(User.class, null, "a.storeId=?", storeId);
	}

	public User getUserByCode(String code) {
		return get(User.class, null, "a.code=?", code);
	}

	public List<Authority> getUserSpecAuths(String userId) {
		return find(Authority.class, null, "a.id in (select b.authId from com.resgain.base.entity.UserAuth b where b.userId=?)", userId);
	}

	public List<Authority> getUserAuths(User user){
		return new ArrayList<Authority>();
	}

	public void saveUserRole(String userId, String roleIds){
		if (isNullOrSpace(userId, roleIds))
			return;
		getSession().createQuery("delete from " + UserRole.class.getName() + " where userId=?").setParameter(0, userId).executeUpdate();
		for (String roleId : roleIds.split(",")) {
			if (isNullOrSpace(roleId))
				continue;
			super.save(new UserRole(userId, roleId.trim()));
		}
	}

	public void saveUserSpecAuth(String userId, String authIds){
		if (isNullOrSpace(userId, authIds))
			return;
		getSession().createQuery("delete from " + UserAuth.class.getName() + " where userId=?").setParameter(0, userId).executeUpdate();
		for (String authId : authIds.split(",")) {
			if (isNullOrSpace(authId))
				continue;
			super.save(new UserAuth(userId, authId.trim()));
		}
	}

	@Override
	public List<DictCode> getDataList(String category, String pcode, String keyword) {
		List<DictCode> ret = new ArrayList<DictCode>();
		List<User> tmp = find(User.class, null, null);
		for (User user : tmp) {
			ret.add(new DictCode(category, user.getId(), null, user.getName()));
		}
		return ret;
	}
}
