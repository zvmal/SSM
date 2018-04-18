package com.wang.inteceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wang.domain.User;

public class SessionCheckInteceptor extends HandlerInterceptorAdapter {

	private List<String> excludedUrls;

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludedUrls = excludeUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub

		System.out.println("======loginPre======");
		// ����session
		HttpSession session = request.getSession();

		// ��ȡ�����ַ
		String url = request.getRequestURL().toString();

		// ���session�е��û�
		User user = (User) session.getAttribute("userToken");

		String requestUri = request.getRequestURI();
		for (String exclude : excludedUrls) {
			if (requestUri.endsWith(exclude)) {
				return true;
			}
		}

		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
		// �ض���
		//
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("======loginAft======");
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

}
