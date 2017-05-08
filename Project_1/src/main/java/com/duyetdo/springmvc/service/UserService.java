package com.duyetdo.springmvc.service;

import java.util.List;

import com.duyetdo.springmvc.model.User;


public interface UserService {
	
	User findById(int id);
	
	User findBySSO(String sso);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserById(int id);

	List<User> findAllUsers(); 
	
	boolean isUserSSOUnique(String sso);

}