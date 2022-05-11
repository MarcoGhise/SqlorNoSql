package it.blog.sqlornosql.dao.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import it.blog.sqlornosql.bean.Author;

@Repository
@Transactional
public class AuthorRepository {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public Optional<Author> findById(Integer id) {
		Author author = entityManager.find(Author.class, id);
		return author != null ? Optional.of(author) : Optional.empty();
	}

	public List<Author> findAll() {
		return entityManager.createQuery("from Author").getResultList();
	}

	public Optional<Author> findByName(String name) {
		Author author = entityManager.createNamedQuery("Author.findByName", Author.class).setParameter("name", name)
				.getSingleResult();
		return author != null ? Optional.of(author) : Optional.empty();
	}

	public Optional<Author> save(Author author) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(author);
			entityManager.getTransaction().commit();
			return Optional.of(author);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
}
