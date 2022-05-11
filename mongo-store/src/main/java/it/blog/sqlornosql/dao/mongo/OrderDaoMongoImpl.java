package it.blog.sqlornosql.dao.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import it.blog.sqlornosql.bean.Basket;
import it.blog.sqlornosql.bean.Order;
import it.blog.sqlornosql.bean.Product;
import it.blog.sqlornosql.dao.OrderDao;

@Component
public class OrderDaoMongoImpl implements OrderDao{

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	ProductDao productDao;
	
	@Override
	public String createOrder(String orderId, String basketId, String userId) {
		/*
		 * 
		 */
		List<Product> products = productDao.getProductWithCatalog(basketId);
		/*
		 * Get basket
		 */
		Basket basket = mongoTemplate.findById(basketId, Basket.class);
		/*
		 * Create Order
		 */
		Order order = new Order();
		order.setOrderId(orderId);
		order.setBasket(basket);
		order.getBasket().setProducts(products);
		
		double amount = basket.getProducts().stream().map(p -> p.getCatalog().getPrice() * p.getQuantity()).reduce(0d, (subtotal, element) -> subtotal + element);
		order.getBasket().setAmount(amount);
		
		mongoTemplate.save(order);
		/*
		 * Store basket with catalog description
		 */
		mongoTemplate.save(order.getBasket());
		
		return orderId;
	}

	@Override
	public Order getOrder(String orderId) {
		/*
		 * Get basket
		 */
		Order order = mongoTemplate.findById(orderId, Order.class);
		
		return order;
	}


}
