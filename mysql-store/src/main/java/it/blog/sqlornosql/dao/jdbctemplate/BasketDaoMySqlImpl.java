package it.blog.sqlornosql.dao.jdbctemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import it.blog.sqlornosql.bean.BasketDashboard;
import it.blog.sqlornosql.bean.Product;
import it.blog.sqlornosql.dao.BasketDao;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BasketDaoMySqlImpl implements BasketDao {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public BasketDashboard getBasketDashboard(String basketId) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", basketId);

		String sql = "SELECT Basket.basketId, User.name as 'firstname', User.surname as 'surname', Product.productId, Product.quantity, Catalog.name, Catalog.description, Catalog.price "
				+ "FROM Basket INNER JOIN User ON Basket.userId = User.userId "
				+ "INNER JOIN Product ON Basket.basketId = Product.basketId "
				+ "INNER JOIN Catalog ON Product.productId = Catalog.productId " + "WHERE Basket.basketId=:id";

		log.info(sql);

		/*
		 * id |name |surname|quantity|name |price|
		 * ------------------------------------+-----+-------+--------+-------+-----+
		 * dbe0d280-b08f-402a-8c87-d68878019df9|Peter|Mitchel| 3|BORRBY | 14.0|
		 * dbe0d280-b08f-402a-8c87-d68878019df9|Peter|Mitchel| 1|SVARTHO| 17.0|
		 */

		List<Map<String, Object>> rows = namedParameterJdbcTemplate.query(sql, namedParameters, new ColumnMapRowMapper());

		BasketDashboard basketDashboard = new BasketDashboard();
		/*
		 * Set basketId
		 */
		basketDashboard.getBasket().setBasketId(String.valueOf(rows.get(0).get("basketId")));
		basketDashboard.getUser().setFirstName(String.valueOf(rows.get(0).get("firstname")));
		basketDashboard.getUser().setSurname(String.valueOf(rows.get(0).get("surname")));

		List<Product> products = new ArrayList<>();

		for (Map<String, Object> r : rows) {
			Product p = new Product();
			p.setBasketId(String.valueOf(rows.get(0).get("basketId")));
			p.setProductId(String.valueOf(r.get("productId")));
			p.setQuantity(Integer.valueOf(String.valueOf(r.get("quantity"))));

			p.getCatalog().setName(String.valueOf(r.get("name")));
			p.getCatalog().setDescription(String.valueOf(r.get("description")));
			p.getCatalog().setPrice(Double.valueOf(String.valueOf(r.get("price"))));

			products.add(p);
		}
		basketDashboard.getBasket().setProducts(products);

		return basketDashboard;

	}

	@Override
	public String addProduct(String basketId, String productId) {
		log.info("Add Product {} for basket {}", productId, basketId);

		/*
		 * Check before insert
		 */
		Map<String, String> parameters = new HashMap<>();
		parameters.put("basketId", basketId);
		parameters.put("productId", productId);

		String sql = "SELECT productId FROM Product WHERE productId=:productId AND basketId=:basketId";

		List<Product> rowsBasket = namedParameterJdbcTemplate.query(sql, parameters,
				new BeanPropertyRowMapper<Product>(Product.class));

		/*
		 * Update/Insert
		 */
		if (rowsBasket.size() > 0)
			/*
			 * Update basket added 1 to quantity
			 */
			sql = "UPDATE Product SET quantity = quantity + 1, updatedAt = CURRENT_TIMESTAMP WHERE productId=:productId AND basketId=:basketId";
		else
			/*
			 * Add product
			 */
			sql = "INSERT INTO Product(productId, basketId, quantity) VALUES(:productId, :basketId, 1)";

		log.info(sql);

		int rowsUpdated = namedParameterJdbcTemplate.update(sql, parameters);

		log.info("Product inserted {}", rowsUpdated);

		return "Success";
	}

	@Override
	public String delProduct(String basketId, String productId) {
		log.info("Delete Product {} for basket {}", productId, basketId);

		/*
		 * Check before insert
		 */
		Map<String, String> parameters = new HashMap<>();
		parameters.put("basketId", basketId);
		parameters.put("productId", productId);

		String sql = "SELECT quantity FROM Product WHERE productId=:productId AND basketId=:basketId";

		List<Product> rowsBasket = namedParameterJdbcTemplate.query(sql, parameters,
				new BeanPropertyRowMapper<Product>(Product.class));

		/*
		 * Update/Insert
		 */
		if (rowsBasket.size() > 0 && rowsBasket.get(0).getQuantity() > 1)
			/*
			 * Update basket remove 1 to quantity
			 */
			sql = "UPDATE Product SET quantity = quantity - 1, updatedAt = CURRENT_TIMESTAMP WHERE productId=:productId AND basketId=:basketId";
		else
			/*
			 * delete product
			 */
			sql = "DELETE FROM Product WHERE productId=:productId AND basketId=:basketId";

		log.info(sql);

		int rowsUpdated = namedParameterJdbcTemplate.update(sql, parameters);

		log.info("Product removed {}", rowsUpdated);

		return "Success";
	}

}
