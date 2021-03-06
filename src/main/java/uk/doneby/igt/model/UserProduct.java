package uk.doneby.igt.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="USER_PRODUCT")
public class UserProduct implements Serializable {

	private static final long serialVersionUID = 8175947726071932787L;
	
	@EmbeddedId
    private UserProductId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
	private User user;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
    @MapsId("id")
	private Product product;
	
	@Column(name="QUANTITY")
	private String quantity;
	
	@Column(name = "BEST_BEFORE_DATE")
	private Date bestBeforeDate;
	
	@Column(name = "CDATE", nullable = false)
	private Date cdate;

	@Column(name = "MDATE", nullable = false)
	private Date mdate;
	
	public UserProduct() {
	}
	
	public UserProduct(User user, Product product) {
		this(user, product, "1");

	}
	
	public UserProduct(User user, Product product, String quantity) {
		this.user = user;
		this.product = product;
		this.quantity = quantity;
		
		id = new UserProductId(user.getId(), product.getId());
		
		System.out.println(product);

	}
	
	@PrePersist
	void onCreate() {
		Date now = new Date();
		this.setCdate(now);
		this.setMdate(now);
	}

	public UserProductId getId() {
		return id;
	}

	public void setId(UserProductId id) {
		this.id = id;
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		product.setQuantity(quantity);
		product.setBestBeforeDate(bestBeforeDate);
		return product;
	}

	public void setProduct(Product product) {
		product.setQuantity(quantity);
		product.setBestBeforeDate(bestBeforeDate);
		this.product = product;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public Date getMdate() {
		return mdate;
	}

	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}

	public Date getBestBeforeDate() {
		return bestBeforeDate;
	}

	public void setBestBeforeDate(Date bestBeforeDate) {
		this.bestBeforeDate = bestBeforeDate;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Product: '").append(product.toString()).append("', ")
		.append("User: '").append(user.toString()).append("', ")
		.append(super.toString());
		return sb.toString();
	}
	

}
