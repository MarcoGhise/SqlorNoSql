package it.blog.sqlornosql.dao.jpa;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import it.blog.sqlornosql.bean.Basket;
import it.blog.sqlornosql.bean.Product;
import it.blog.sqlornosql.bean.ProductKey;
import it.blog.sqlornosql.dao.BasketDao;

@Repository
@Transactional
public class BasketDaoJPAImpl implements BasketDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Basket getBasketDashboard(String basketId) {

		Basket basket = em.find(Basket.class, basketId);

		return basket;
	}

	@Override
	public String addProduct(String basketId, String productId) {
		/*
		 * Get the basket
		 */
		Basket basket = em.find(Basket.class, basketId);

		ProductKey pk = new ProductKey();
		pk.setBasketId(basketId);
		pk.setProductId(productId);
		/*
		 * Get products
		 */
		if (basket.getProducts().size() == 0) {
			/*
			 * Create product
			 */
			Product p = this.createProduct(basket, pk);

			em.persist(p);

		} else {

			Optional<Product> pOptional = this.getProductByKey(pk);			
			Product p = null;
			
			if (pOptional.isPresent())
			{
				p = pOptional.get();
				p.setQuantity(p.getQuantity() + 1);
			}
			else				
				p = this.createProduct(basket, pk);

			em.persist(p);
		}
		/*
		 * Set update time
		 */
		basket.setLocalDateTime(LocalDateTime.now());
		em.persist(basket);
		
		return "Success";
	}
	
	private Optional<Product> getProductByKey(ProductKey pk)
	{
		Product p = em.find(Product.class, pk);
		return p != null ? Optional.of(p) : Optional.empty();
	}

	private Product createProduct(Basket basket, ProductKey pk) {
		Product p = new Product();
		p.setProductKey(pk);
		p.setQuantity(1);
		p.setBasketProduct(basket);

		return p;
	}

	@Override
	public String delProduct(String basketId, String productId) {
		/*
		 * Get the basket
		 */
		Basket basket = em.find(Basket.class, basketId);
		/*
		 * Get products
		 */
		Optional<Product> productOptional = basket.getProducts().stream()
				.filter(p -> p.getProductKey().getProductId().equals(productId)).findFirst();

		if (productOptional.isPresent()) {

			Product product = productOptional.get();

			if (product.getQuantity() > 1) {
				product.setQuantity(product.getQuantity() - 1);
				em.persist(product);
			} else {
				em.remove(product);
			}
		}		
		/*
		 * Set update time
		 */
		basket.setLocalDateTime(LocalDateTime.now());
		em.persist(basket);
		
		return "Success";
	}

}
