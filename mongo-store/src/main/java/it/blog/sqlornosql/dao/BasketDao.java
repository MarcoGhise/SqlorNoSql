package it.blog.sqlornosql.dao;

import it.blog.sqlornosql.bean.Basket;

public interface BasketDao {

	public Basket getBasketDashboard(String basketId);
	
	public String addProduct(String basketId, String productId);
	
	public String delProduct(String basketId, String productId);

}
