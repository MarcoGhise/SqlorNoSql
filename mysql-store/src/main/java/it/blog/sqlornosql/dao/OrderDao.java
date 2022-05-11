package it.blog.sqlornosql.dao;

import it.blog.sqlornosql.bean.OrderDashboard;

public interface OrderDao {

	public String createOrder(String orderId, String basketId, double amount);
	
	public OrderDashboard getOrder(String orderId);
}
