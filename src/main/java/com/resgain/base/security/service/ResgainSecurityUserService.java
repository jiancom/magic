package com.resgain.base.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.resgain.base.entity.Authority;
import com.resgain.base.entity.User;
import com.resgain.base.security.bean.ResgainUser;
import com.resgain.base.service.UserService;

/**
 * Spring Security的UserDetailsService接口实现类，实现用户认证的入口
 * @author memphis.guo
 */
@Component
public class ResgainSecurityUserService implements UserDetailsService
{
	@Autowired private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException, DataAccessException {
		User emp = userService.getUserByCode(loginId);
		if(emp==null)
			throw new UsernameNotFoundException("用户[" + loginId + " ]不存在");
		List<GrantedAuthority> t = new ArrayList<GrantedAuthority>();
		t.add(new GrantedAuthority(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getAuthority() {
				return "ROLE_USER";
			}
		});
		for (final Authority auth : userService.getUserAuths(emp)) {
			t.add(new GrantedAuthority(){
				private static final long serialVersionUID = 1L;
				@Override
				public String getAuthority() {
					return auth.getCode();
				}
			});
		}
		ResgainUser ret = new ResgainUser(emp, emp.getId(), emp.getCode(), emp.getPass(), new Integer(emp.getStatus()) < 3, t);
		return ret;
	}
}