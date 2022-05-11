package it.blog.sqlornosql.bean;

public class OrderDashboard {

	Basket basket;
	User user;
	double amount;
	
	public OrderDashboard()
	{
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
