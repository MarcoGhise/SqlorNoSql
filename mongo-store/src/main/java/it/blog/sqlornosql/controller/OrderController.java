package it.blog.sqlornosql.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.blog.sqlornosql.bean.Basket;
import it.blog.sqlornosql.bean.Order;
import it.blog.sqlornosql.dao.BasketDao;
import it.blog.sqlornosql.dao.OrderDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class OrderController {
	
	@Autowired
	BasketDao basketDao;
	
	@Autowired
	OrderDao orderDao;

	@GetMapping("/order/{orderId}")
	public String getOrder(@PathVariable String orderId, Model model) {

		Order order = orderDao.getOrder(orderId);
		
		model.addAttribute("firstname", order.getBasket().getUser().getFirstName());
		model.addAttribute("surname", order.getBasket().getUser().getSurname());		
	
		
		model.addAttribute("amount", order.getBasket().getAmount());	
		model.addAttribute("size", order.getBasket().getItem());
		
		model.addAttribute("products",order.getBasket().getProducts());
		
		model.addAttribute("userId", order.getBasket().getUser().getUuid());

		return "order";
	}
	
	@PostMapping(value="/order/{basketId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Map<String, Object> createOrder(@PathVariable String basketId, Model model) {
		
		log.info("Create order for basket {}", basketId);
		
		/*
		 * Check before insert
		 */
		Basket basket = basketDao.getBasketDashboard(basketId);		
		/*
		 * Create Order
		 */
		if (basket.getProducts().size()>0) {								
						
			String orderId = UUID.randomUUID().toString();
			
			orderDao.createOrder(orderId, basketId, basket.getUser().getUuid());
			
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put("orderId", orderId);
	    return response;
			
		}
		
		log.info("Basket not found");
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("result", "Failure");
    return response;
		
	}
}
