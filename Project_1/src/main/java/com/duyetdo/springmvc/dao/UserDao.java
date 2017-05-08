package com.duyetdo.springmvc.dao;

import java.util.List;

import com.duyetdo.springmvc.model.User;


public interface UserDao {

	User findById(int id);
	
	User findBySSO(String sso);
	
	void save(User user);
	
	void deleteById(int id);
	
	List<User> findAllUsers();

}

