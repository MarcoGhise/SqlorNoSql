package it.blog.sqlornosql.dao.jdbctemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import it.blog.sqlornosql.bean.OrderDashboard;
import it.blog.sqlornosql.bean.Product;
import it.blog.sqlornosql.dao.OrderDao;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderDaoMySqlImpl implements OrderDao {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public String createOrder(String orderId, String basketId, double amount) {
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();		
		
		sqlParams.addValue("orderId", orderId, java.sql.Types.VARCHAR);
		sqlParams.addValue("basketId", basketId, java.sql.Types.VARCHAR);		
		sqlParams.addValue("amount", amount, java.sql.Types.DOUBLE);					
	
		/*
		 * Update Basket with orderId
		 */
		String sql = "UPDATE Basket SET orderId=:orderId, updatedAt = CURRENT_TIMESTAMP WHERE basketId=:basketId";
		
		log.info(sql);
		
		int rowsUpdated = namedParameterJdbcTemplate.update(sql, sqlParams);
		
		log.info("Basket updated {}", rowsUpdated);
		
		/*
		 * Create order
		 */
		sql = "INSERT INTO `Order`(orderId, basketId, amount) VALUES(:orderId, :basketId, :amount)";
		
		log.info(sql);
		
		rowsUpdated = namedParameterJdbcTemplate.update(sql, sqlParams);
		
		log.info("Order created {}", rowsUpdated);
		
		return "Success";
	}

	@Override
	public OrderDashboard getOrder(String orderId) {
		
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", orderId);

		String sql = "SELECT `Order`.amount, `User`.name as 'firstname', `User`.surname as 'surname', Product.productId, Product.quantity, Catalog.name, Catalog.description, Catalog.price "
				+ "FROM `Order` "
				+ "INNER JOIN Basket ON `Order`.basketId = `Basket`.basketId "
				+ "INNER JOIN `User` ON `Basket`.userId = `User`.userId " 
				+ "INNER JOIN Product ON `Basket`.basketId = Product.basketId " 
				+ "INNER JOIN Catalog ON Product.productId = Catalog.productId " 
				+ "WHERE `Order`.orderId=:id";

		log.info(sql);

		/*
		 * id |name |surname|quantity|name |price|
		 * ------------------------------------+-----+-------+--------+-------+-----+
		 * dbe0d280-b08f-402a-8c87-d68878019df9|Peter|Mitchel| 3|BORRBY | 14.0|
		 * dbe0d280-b08f-402a-8c87-d68878019df9|Peter|Mitchel| 1|SVARTHO| 17.0|
		 */

		List<Map<String, Object>> rows = namedParameterJdbcTemplate.query(sql, namedParameters, new ColumnMapRowMapper());

		OrderDashboard orderDashboard = new OrderDashboard();
	
		orderDashboard.getUser().setFirstName(String.valueOf(rows.get(0).get("firstname")));
		orderDashboard.getUser().setSurname(String.valueOf(rows.get(0).get("surname")));
		orderDashboard.setAmount(Double.valueOf(String.valueOf(rows.get(0).get("amount"))));

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
		orderDashboard.getBasket().setProducts(products);

		return orderDashboard;

	}

}
