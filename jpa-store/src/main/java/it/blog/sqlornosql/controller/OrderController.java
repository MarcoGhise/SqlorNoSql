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

		Order orderDashboard = orderDao.getOrder(orderId);
		
		model.addAttribute("firstname", orderDashboard.getBasketOrder().getUserBasket().getFirstName());
		model.addAttribute("surname", orderDashboard.getBasketOrder().getUserBasket().getSurname());		
	
		
		model.addAttribute("amount", orderDashboard.getAmount());	
		model.addAttribute("size", orderDashboard.getBasketOrder().getProducts().size());
		
		model.addAttribute("products",orderDashboard.getBasketOrder().getProducts());
		
		model.addAttribute("userId", orderDashboard.getBasketOrder().getUserBasket().getUuid());

		return "order";
	}
	
	@PostMapping(value="/order/{basketId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Map<String, Object> createOrder(@PathVariable String basketId, Model model) {
				
		log.info("Create order for basket {}", basketId);
		
		/*
		 * Check before insert
		 */
		Basket basketDashboard = basketDao.getBasketDashboard(basketId);		
		/*
		 * Create Order
		 */
		if (basketDashboard.getProducts().size()>0) {								
			/*
			 * Calculate the amount
			 */
			double amount = basketDashboard.getProducts().stream().map(p -> p.getProductCatalog().getPrice() * p.getQuantity()).reduce(0d, (subtotal, element) -> subtotal + element);
						
			String orderId = UUID.randomUUID().toString();
			
			orderDao.createOrder(orderId, basketId, amount);
			
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
