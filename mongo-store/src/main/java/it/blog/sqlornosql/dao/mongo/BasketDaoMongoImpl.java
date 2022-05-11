package it.blog.sqlornosql.dao.mongo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

//import com.mongodb.BasicDBObject;

import it.blog.sqlornosql.bean.Basket;
import it.blog.sqlornosql.bean.Product;
import it.blog.sqlornosql.dao.BasketDao;

@Component
public class BasketDaoMongoImpl implements BasketDao {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	ProductDao productDao;

	@Override
	public Basket getBasketDashboard(String basketId) {
		/*
		 * Get basket
		 */
		List<Product> products = productDao.getProductWithCatalog(basketId);
		
		Basket currentBasket =  mongoTemplate.findById(basketId, Basket.class);
		/*
		 * Set products with Catalog information
		 */
		currentBasket.setProducts(products);
		
		return currentBasket;
	}

	@Override
	public String addProduct(String basketId, String productId) {
		/*
		 * Get basket
		 */
		Basket basket = mongoTemplate.findById(basketId, Basket.class);
		/*
		 * Add product
		 */
		Optional<Product> product = basket.getProducts().stream().filter(p -> p.getProductId().equals(productId))
				.findFirst();

		if (product.isPresent()) {
			product.get().setQuantity(product.get().getQuantity() + 1);
		} else {
			
			Product newProduct = new Product();
			newProduct.setProductId(productId);
			newProduct.setQuantity(1);

			basket.getProducts().add(newProduct);
		}
		/*
		 * Compute size
		 */
		basket.setItem(this.getItemSize(basket));
		/*
		 * Save basket
		 */
		mongoTemplate.save(basket);

		return basketId;
	}

	@Override
	public String delProduct(String basketId, String productId) {
		/*
		 * Get basket
		 */
		Basket basket = mongoTemplate.findById(basketId, Basket.class);
		/*
		 * Add product
		 */
		Optional<Product> product = basket.getProducts().stream().filter(p -> p.getProductId().equals(productId))
				.findFirst();

		if (product.isPresent())
			if (product.get().getQuantity() > 1)
				product.get().setQuantity(product.get().getQuantity() - 1);
			else
				basket.getProducts().remove(product.get());
		/*
		 * Compute size
		 */
		basket.setItem(this.getItemSize(basket));
		/*
		 * Save basket
		 */
		mongoTemplate.save(basket);

		return basketId;
	}

	private int getItemSize(Basket basket) {
		return basket.getProducts().size();
	}
}
