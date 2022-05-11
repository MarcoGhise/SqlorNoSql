package it.blog.sqlornosql.dao.mongo;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import it.blog.sqlornosql.bean.Catalog;
import it.blog.sqlornosql.dao.CatalogDao;

@Component
public class CatalogDaoMongoImpl implements CatalogDao, InitializingBean{

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public List<Catalog> getCatalog() {
		return mongoTemplate.findAll(Catalog.class);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		mongoTemplate.remove(new Query(), Catalog.class);
		
		/*
		 * ('0db3401f-3085-47c6-903a-8015d17a803e','APPLARO','3-seat modular sofa, outdoor, with footstool brown',300,'2022-04-20 09:52:22',NULL),
		 */
		Catalog p1 = new Catalog();
		p1.setProductId("0db3401f-3085-47c6-903a-8015d17a803e");
		p1.setName("APPLARO");
		p1.setDescription("3-seat modular sofa, outdoor, with footstool brown");
		p1.setPrice(300);
		/*
		 * ('9caf0ffc-a945-4551-bc6c-00ad6e20caaf','BORRBY','Lantern for block candle, in/outdoor white',14,'2022-04-20 09:53:25',NULL),
		 */
		Catalog p2 = new Catalog();
		p2.setProductId("9caf0ffc-a945-4551-bc6c-00ad6e20caaf");
		p2.setName("BORRBY");
		p2.setDescription("Lantern for block candle, in/outdoor white");
		p2.setPrice(14);
		/*
		 * ('d8a03eca-53af-4dee-a4e4-19081caa3ad6','SVARTHO','Cushion cover, beige',17,'2022-04-20 09:54:30',NULL);
		 */
		Catalog p3 = new Catalog();
		p3.setProductId("d8a03eca-53af-4dee-a4e4-19081caa3ad6");
		p3.setName("SVARTHO");
		p3.setDescription("Cushion cover, beige");
		p3.setPrice(17);
		
		mongoTemplate.save(p1);
		mongoTemplate.save(p2);
		mongoTemplate.save(p3);
		
	}

}
