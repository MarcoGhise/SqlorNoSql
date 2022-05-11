package it.blog.sqlornosql.dao.jpa;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import it.blog.sqlornosql.bean.Basket;
import it.blog.sqlornosql.bean.User;
import it.blog.sqlornosql.dao.UserDao;

@Repository
@Transactional
public class UserDaoJPAImpl implements UserDao{

	@PersistenceContext
  private EntityManager em;
	
	@Override
	public String createUser(User user) {
		
		/*
		 * Create Basket
		 */
		String basketId = UUID.randomUUID().toString();
		
		Basket basket = new Basket();
		basket.setBasketId(basketId);
		basket.setUserBasket(user);

		Set<Basket> basketSet = new HashSet<>();
		basketSet.add(basket);
		
		user.setBaskets(basketSet);
		
		em.persist(user);
		em.persist(basket);
		
		return basketId;
	}

	@Override
	public String createExistingUser(String userId) {
		
		User user = em.find(User.class, userId);		
		/*
		 * Create Basket
		 */
		String basketId = UUID.randomUUID().toString();
		
		Basket basket = new Basket();
		basket.setBasketId(basketId);
		basket.setUserBasket(user);

		Set<Basket> basketSet = new HashSet<>();
		basketSet.add(basket);
	
		em.persist(basket);
		
		return basketId;
	}	
}