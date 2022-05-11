package it.blog.sqlornosql.dao.jdbctemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import it.blog.sqlornosql.bean.User;
import it.blog.sqlornosql.dao.UserDao;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserDaoMySqlImpl implements UserDao{

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public String createUser(User user) {
		
		Map<String, String> parameters = new HashMap<>();
		parameters.put("uuid", user.getUuid());
		parameters.put("firstname", user.getFirstName());
		parameters.put("surname", user.getSurname());
		
		String sql = "INSERT INTO User(userId, name, surname) VALUES(:uuid,:firstname,:surname)";
		
		int rowsUpdated = namedParameterJdbcTemplate.update(sql, parameters);
		
		log.info("User inserted {}", rowsUpdated);
		
		String basketId = UUID.randomUUID().toString();
		
		parameters.clear();
		parameters.put("id", basketId);
		parameters.put("userId", user.getUuid());
		/*
		 * Create also a Basket
		 */
		sql = "INSERT INTO Basket(basketId, userId) VALUES(:id,:userId)";
		
		rowsUpdated = namedParameterJdbcTemplate.update(sql, parameters);
		
		log.info("User inserted {}", rowsUpdated);
		
		return basketId;
	}

}
