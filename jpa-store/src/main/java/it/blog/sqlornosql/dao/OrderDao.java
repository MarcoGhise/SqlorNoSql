package it.blog.sqlornosql.dao;

import it.blog.sqlornosql.bean.Order;

public interface OrderDao {

	public String createOrder(String orderId, String basketId, double amount);
	
	public Order getOrder(String orderId);
}
