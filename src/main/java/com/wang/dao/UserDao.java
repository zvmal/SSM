package com.wang.dao;

import com.wang.domain.User;

public interface UserDao {
   public abstract User findByUserName(String userName);
}
