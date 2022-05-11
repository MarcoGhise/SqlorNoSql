package it.blog.sqlornosql.bean;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Basket {

	@Id
	private String basketId;

	@Column(name="orderId")
	private String orderId;
	
	@Transient
	private int item;
	
	@Transient
	private double amount;
	
	@OneToMany(mappedBy="basketProduct", fetch = FetchType.EAGER)
	private Set<Product> products;
	
/*	@OneToOne(mappedBy="basketOrder")
	private Order order;
*/	
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User userBasket;
	
	@Column(name="updatedAt")
	LocalDateTime localDateTime;
		
	public String getBasketId() {
		return basketId;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}

	public int getItem() {
		return item;
	}
	public void setItem(int item) {
		this.item = item;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Set<Product> getProducts() {
		return products;
	}
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public User getUserBasket() {
		return userBasket;
	}
	public void setUserBasket(User userBasket) {
		this.userBasket = userBasket;
	}
//	public Order getOrder() {
//		return order;
//	}
//	public void setOrder(Order order) {
//		this.order = order;
//	}
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

}
