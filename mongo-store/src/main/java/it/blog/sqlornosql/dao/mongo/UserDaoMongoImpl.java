package it.blog.sqlornosql.dao.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import it.blog.sqlornosql.bean.Basket;
import it.blog.sqlornosql.bean.Order;
import it.blog.sqlornosql.bean.User;
import it.blog.sqlornosql.dao.UserDao;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserDaoMongoImpl implements UserDao {

	@Autowired
	MongoTemplate mongoTemplate;

	public String createUser(User user) {
		
		String basketId = UUID.randomUUID().toString();

		Basket basket = new Basket();

		basket.setBasketId(basketId);
		basket.setUser(user);
		/*
		 * Create empty basket
		 */
		basket.setProducts(new ArrayList<>());

		/*
		 * Insert into MongoDb
		 */
		Basket basketSaved = mongoTemplate.save(basket);

		log.info("basket inserted {}", basketSaved);

		return basketId;
	}

	@Override
	public String createExistingUser(String userId) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("basket.user._id").is(userId));
		
		List<Order> order =  mongoTemplate.find(query, Order.class);
		
		String basketId = UUID.randomUUID().toString();

		Basket basket = new Basket();

		basket.setBasketId(basketId);
		basket.setUser(order.get(0).getBasket().getUser());
		/*
		 * Create empty basket
		 */
		basket.setProducts(new ArrayList<>());
		/*
		 * Insert into MongoDb
		 */
		Basket basketSaved = mongoTemplate.save(basket);

		log.info("basket inserted {}", basketSaved);

		return basketId;
	}

}
