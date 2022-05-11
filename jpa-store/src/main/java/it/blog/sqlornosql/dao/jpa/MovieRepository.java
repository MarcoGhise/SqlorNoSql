package it.blog.sqlornosql.dao.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import it.blog.sqlornosql.bean.Movie;

public class MovieRepository {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public Optional<Movie> save(Movie movie) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(movie);
			entityManager.getTransaction().commit();
			return Optional.of(movie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public Optional<Movie> findById(Integer id) {
		Movie movie = entityManager.find(Movie.class, id);
		return movie != null ? Optional.of(movie) : Optional.empty();
	}

	public List<Movie> findAll() {
		return entityManager.createQuery("from Movie").getResultList();
	}

	public void deleteById(Integer id) {
		// Retrieve the movie with this ID
		Movie movie = entityManager.find(Movie.class, id);
		if (movie != null) {
			try {
				// Start a transaction because we're going to change the database
				entityManager.getTransaction().begin();

				// Remove all references to this movie by superheroes
				movie.getSuperHeroes().forEach(superHero -> {
					superHero.getMovies().remove(movie);
				});

				// Now remove the movie
				entityManager.remove(movie);

				// Commit the transaction
				entityManager.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
