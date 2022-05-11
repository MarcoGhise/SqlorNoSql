package it.blog.sqlornosql.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import it.blog.sqlornosql.bean.User;
import it.blog.sqlornosql.dao.UserDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {

	@Autowired
	UserDao userDao;
	
	@GetMapping("/")
	public String showUserForm() {		
		
		return "user";
	}

	@PostMapping(value="/user",	consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public RedirectView createUserForm(@RequestParam Map<String, String> name) {
		
		String userId = UUID.randomUUID().toString();

		User user = new User();
		user.setUuid(userId);
		user.setFirstName(name.get("firstname"));
		user.setSurname(name.get("surname"));
		
		/*
		 * Create User
		 */
		String basketId = userDao.createUser(user);
		
		log.info("Basket created {}", basketId);
		
		return new RedirectView(String.format("catalog/%s", basketId));
	}
	
	@PostMapping(value="/user/{userId}",	produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Map<String, Object> createExistsUserForm(@PathVariable String userId) {
		/*
		 * Create User
		 */
		String basketId = userDao.createExistingUser(userId);
		
		log.info("Basket created {}", basketId);
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("basketId", basketId);
    return response;
	}
}
