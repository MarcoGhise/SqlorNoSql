package it.blog.sqlornosql.bean;

public class BasketDashboard {
	
	Basket basket;
	User user;
	
	public BasketDashboard() {
		this.basket = new Basket();
		this.user = new User();
	}
	
	public Basket getBasket() {
		return basket;
	}
	public void setBasket(Basket basket) {
		this.basket = basket;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
