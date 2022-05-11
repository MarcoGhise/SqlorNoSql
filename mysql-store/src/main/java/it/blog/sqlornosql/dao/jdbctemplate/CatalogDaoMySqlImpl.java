package it.blog.sqlornosql.dao.jdbctemplate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import it.blog.sqlornosql.bean.Catalog;
import it.blog.sqlornosql.dao.CatalogDao;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CatalogDaoMySqlImpl implements CatalogDao {
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<Catalog> getCatalog() {
		
		String sql = "SELECT productId, name, description, price FROM `Catalog`";

		log.info(sql);

/*
 * productId                           |name   |description                                       |price|
* ------------------------------------+-------+--------------------------------------------------+-----+
* 0db3401f-3085-47c6-903a-8015d17a803e|APPLARO|3-seat modular sofa, outdoor, with footstool brown|300.0|
* 9caf0ffc-a945-4551-bc6c-00ad6e20caaf|BORRBY |Lantern for block candle, in/outdoor white        | 14.0|
* d8a03eca-53af-4dee-a4e4-19081caa3ad6|SVARTHO|Cushion cover, beige                              | 17.0|
 */

		List<Catalog> rows = namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<Catalog>(Catalog.class));
		
		return rows;
	}

}
