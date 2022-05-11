package it.blog.sqlornosql.dao;

import it.blog.sqlornosql.bean.BasketDashboard;

public interface BasketDao {

	public BasketDashboard getBasketDashboard(String basketId);
	
	public String addProduct(String basketId, String productId);
	
	public String delProduct(String basketId, String productId);

}
