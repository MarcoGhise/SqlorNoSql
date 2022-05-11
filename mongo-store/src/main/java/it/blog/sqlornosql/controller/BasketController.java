package it.blog.sqlornosql.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.blog.sqlornosql.bean.Basket;
import it.blog.sqlornosql.dao.BasketDao;

@Controller
public class BasketController {
	
	@Autowired
	BasketDao basketDao;
	
	double amountShipping = 5;

	@GetMapping("/basket/{basketId}")
	public String getBasket(@PathVariable String basketId, Model model) {
		
		Basket basket = basketDao.getBasketDashboard(basketId);	
		
		model.addAttribute("firstname", basket.getUser().getFirstName());	
		model.addAttribute("surname", basket.getUser().getSurname());
		
		double amount = basket.getProducts().stream().map(p -> p.getCatalog().getPrice() * p.getQuantity()).reduce(0d, (subtotal, element) -> subtotal + element);
		
		model.addAttribute("amount", amount);	
		model.addAttribute("size", basket.getItem());
		
		model.addAttribute("products",basket.getProducts());
		
		model.addAttribute("amountShipping", amount + amountShipping);

		return "dashboard";
	}
	
	@PostMapping(value="/basket/{basketId}/{productId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Map<String, Object> addProductBasket(@PathVariable String basketId, @PathVariable String productId, Model model) {			
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("result", basketDao.addProduct(basketId, productId));
    return response;
		
	}
	
	@DeleteMapping(value="/basket/{basketId}/{productId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Map<String, Object> delProductBasket(@PathVariable String basketId, @PathVariable String productId, Model model) {
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("result", basketDao.delProduct(basketId, productId));
    return response;
		
	}
}