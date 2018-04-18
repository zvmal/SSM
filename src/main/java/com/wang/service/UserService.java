package com.wang.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wang.dao.UserDao;
import com.wang.domain.User;

@Service
public class UserService {
	@Resource
   private UserDao userDao;
	
   public User findByUserName(String userName){
	   return userDao.findByUserName(userName);
   };
}
