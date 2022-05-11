package it.blog.sqlornosql.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import it.blog.sqlornosql.bean.Catalog;
import it.blog.sqlornosql.dao.CatalogDao;

@Repository
public class CatalogDaoJPAImpl implements CatalogDao{

	@PersistenceContext
  private EntityManager em;
	
	@Override
	public List<Catalog> getCatalog() {
		return (List<Catalog>) em.createNamedQuery("Catalog.findAll").getResultList();
	}

}
