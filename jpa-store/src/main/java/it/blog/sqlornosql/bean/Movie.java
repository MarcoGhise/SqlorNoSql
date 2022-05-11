package it.blog.sqlornosql.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/*
 * https://www.infoworld.com/article/3387643/java-persistence-with-jpa-and-hibernate-part-2-many-to-many-relationships.html
 */

@Entity
@Table(name = "MOVIE")
public class Movie {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;

    @ManyToMany(mappedBy = "movies", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<SuperHero> superHeroes = new HashSet<>();

    public Movie() {
    }

    public Movie(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Movie(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<SuperHero> getSuperHeroes() {
        return superHeroes;
    }

    public void addSuperHero(SuperHero superHero) {
        superHeroes.add(superHero);
        superHero.getMovies().add(this);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}