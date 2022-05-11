package it.blog.sqlornosql.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/*
 * https://stackoverflow.com/questions/29952386/embedded-id-and-repeated-column-in-mapping-for-entity-exception
 * 
 * Aborted solution
 * https://stackoverflow.com/questions/31385658/jpa-how-to-make-composite-foreign-key-part-of-composite-primary-key
 * 
 * Explanation
 * https://www.baeldung.com/jpa-composite-primary-keys
 */

@Entity
public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3688755328231199883L;

	@EmbeddedId
	private ProductKey productKey;
	
	@Column(name="quantity")
  private int quantity;  
  
  @OneToOne
  @JoinColumn(name = "productId", nullable = false, insertable = false, updatable = false)
  private Catalog productCatalog;
  
  @ManyToOne
  @JoinColumn(name = "basketId", nullable = false, insertable = false, updatable = false)
  private Basket basketProduct;
  
  public Product() {
  }

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Basket getBasketProduct() {
		return basketProduct;
	}

	public void setBasketProduct(Basket basketProduct) {
		this.basketProduct = basketProduct;
	}

	public Catalog getProductCatalog() {
		return productCatalog;
	}

	public void setProductCatalog(Catalog productCatalog) {
		this.productCatalog = productCatalog;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == this)
	        return true;
	    if (!(o instanceof Product))
	        return false;
	    Product other = (Product)o;	    
	    return this.productKey.getProductId().equals(other.productKey.getProductId()) && this.productKey.getBasketId().equals(other.productKey.getBasketId());
	}
	
	@Override
	public final int hashCode() {
	    int result = 17;
	    if (this.productKey.getProductId() != null) {
	        result = 31 * result + this.productKey.getProductId().hashCode();
	    }
	    if (this.productKey.getBasketId() != null) {
	        result = 31 * result + this.productKey.getBasketId().hashCode();
	    }
	    return result;
	}

	public ProductKey getProductKey() {
		return productKey;
	}

	public void setProductKey(ProductKey productKey) {
		this.productKey = productKey;
	}
}
