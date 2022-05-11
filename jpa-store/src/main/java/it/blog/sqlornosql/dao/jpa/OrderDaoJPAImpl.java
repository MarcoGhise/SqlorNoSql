package it.blog.sqlornosql.dao.jpa;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import it.blog.sqlornosql.bean.Basket;
import it.blog.sqlornosql.bean.Order;
import it.blog.sqlornosql.dao.OrderDao;

@Repository
@Transactional
public class OrderDaoJPAImpl implements OrderDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public String createOrder(String orderId, String basketId, double amount) {

		/*
		 * Get the basket
		 */
		Basket basket = em.find(Basket.class, basketId);
		/*
		 * Update order date
		 */
		basket.setLocalDateTime(LocalDateTime.now());
		/*
		 * Set orderId
		 */
		basket.setOrderId(orderId);
		/*
		 * Set update time
		 */
		basket.setLocalDateTime(LocalDateTime.now());
		em.persist(basket);;
		/*
		 * Create Order
		 */
		Order order = new Order();
		order.setOrderId(orderId);
		order.setAmount(amount);
		order.setBasketOrder(basket);

		em.persist(order);
		return "Success";
	}

	@Override
	public Order getOrder(String orderId) {

		Order order = em.find(Order.class, orderId);
		
		return order;
	}
}