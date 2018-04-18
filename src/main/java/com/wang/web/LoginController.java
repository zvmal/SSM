package com.wang.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wang.annotation.SystemLog;
import com.wang.dao.UserDao;
import com.wang.domain.User;
import com.wang.service.UserService;

/**
 * 用户控制器 
 */
@Controller
public class LoginController {
	@Resource
	private UserDao userDao;
	@Resource
	private UserService userService;
	
	@RequestMapping(value = "/" , method = RequestMethod.GET)
	public ModelAndView home(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		return mav;
	}
	
	@RequestMapping(value = "/login" , method = RequestMethod.POST)
	@SystemLog(methods = "用户管理",module = "用户登陆")
	public ModelAndView login(User model , HttpSession session){
		User user = userService.findByUserName(model.getUsername());
		
		if (user == null || !user.getPassword().equals(model.getPassword())){
			return new ModelAndView("redirect:/login.jsp");
		} else {
			session.setAttribute("user", user);
			ModelAndView mav = new ModelAndView();
			mav.setViewName("index");
			return mav;
		}
		
	}


}
