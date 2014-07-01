package com.resgain.base.security.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.resgain.dragon.filter.ActionUtil;

@Component
public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler
{
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		ActionUtil.outJsonMsg(request, response, false, "用户名/密码错误", null);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		ActionUtil.outJsonMsg(request, response, true, "登录成功", null);
	}
}
