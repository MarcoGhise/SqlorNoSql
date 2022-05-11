package it.blog.sqlornosql.dao.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import it.blog.sqlornosql.bean.Basket;
import it.blog.sqlornosql.bean.Product;

@Component
public class ProductDao {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public List<Product> getProductWithCatalog(String basketId){
		
		/*
		 * https://stackoverflow.com/questions/53108493/merge-lookup-result-to-existing-array
		 */		
		LookupOperation lookup = LookupOperation.newLookup()
        .from("catalog")
        .localField("products._id")
        .foreignField("_id")
        .as("products.catalog");
		
		Aggregation aggregation = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("_id").is(basketId)),
				Aggregation.unwind("products"),				
				lookup,
				Aggregation.unwind("products.catalog"),
				Aggregation.group("_id").push("products").as("products"));
		
		Basket productsWithCatalog = mongoTemplate.aggregate(aggregation, "basket", Basket.class).getMappedResults().stream().findFirst().get();
		
		return productsWithCatalog.getProducts();
	}

}
