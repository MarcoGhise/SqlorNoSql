package it.blog.sqlornosql.dao.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import it.blog.sqlornosql.bean.SuperHero;

public class SuperHeroRepository {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public Optional<SuperHero> save(SuperHero superHero) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(superHero);
			entityManager.getTransaction().commit();
			return Optional.of(superHero);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public Optional<SuperHero> findById(Integer id) {
		SuperHero superHero = entityManager.find(SuperHero.class, id);
		return superHero != null ? Optional.of(superHero) : Optional.empty();
	}

	public List<SuperHero> findAll() {
		return entityManager.createQuery("from SuperHero").getResultList();
	}

	public void deleteById(Integer id) {
		// Retrieve the movie with this ID
		SuperHero superHero = entityManager.find(SuperHero.class, id);
		if (superHero != null) {
			try {
				// Start a transaction because we're going to change the database
				entityManager.getTransaction().begin();

				// Remove all references to this superhero in its movies
				superHero.getMovies().forEach(movie -> {
					movie.getSuperHeroes().remove(superHero);
				});

				// Now remove the superhero
				entityManager.remove(superHero);

				// Commit the transaction
				entityManager.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
