package it.blog.sqlornosql.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="`Order`")
public class Order {
	@Id
	String orderId;

	double amount;

	@OneToOne
	@JoinColumn(name = "basketId", nullable = true)
	private Basket basketOrder;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Basket getBasketOrder() {
		return basketOrder;
	}

	public void setBasketOrder(Basket basketOrder) {
		this.basketOrder = basketOrder;
	}
}
