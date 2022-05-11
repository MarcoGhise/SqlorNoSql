package it.blog.sqlornosql.dao;

import it.blog.sqlornosql.bean.User;

public interface UserDao {

	public String createUser(User user);
	
	public String createExistingUser(String userId);
}
