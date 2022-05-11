package it.blog.sqlornosql.bean;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;

@Entity
public class User {

	@Id
	@Column(name="userId")
	private String uuid;
	@NotNull
	@Column(name="name") 
	private String firstName;
	
	@OneToMany(mappedBy="userBasket")
	private Set<Basket> baskets;
	
	@NotNull
	private String surname;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Set<Basket> getBaskets() {
		return baskets;
	}
	public void setBaskets(Set<Basket> baskets) {
		this.baskets = baskets;
	}
}
