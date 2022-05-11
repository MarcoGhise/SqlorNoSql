package it.blog.sqlornosql.bean;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ProductKey implements Serializable{

  /**
	 * 
	 */
	private static final long serialVersionUID = 7115047742805031757L;
	private String productId;
  private String basketId;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getBasketId() {
		return basketId;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == this)
	        return true;
	    if (!(o instanceof ProductKey))
	        return false;
	    ProductKey other = (ProductKey)o;	    
	    return this.productId.equals(other.getProductId()) && this.basketId.equals(other.getBasketId());
	}
	
	@Override
	public final int hashCode() {
	    int result = 17;
	    if (productId != null) {
	        result = 31 * result + productId.hashCode();
	    }
	    if (basketId != null) {
	        result = 31 * result + basketId.hashCode();
	    }
	    return result;
	}
}
