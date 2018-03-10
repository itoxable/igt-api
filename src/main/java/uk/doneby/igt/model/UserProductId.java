package uk.doneby.igt.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserProductId implements Serializable {
 
	private static final long serialVersionUID = 8739833427479524227L;

	@Column(name = "user_id")
    private Long userId;
 
    @Column(name = "product_id")
    private Long productId;
 
    private UserProductId() {}
 
    public UserProductId(Long userId,  Long productId) {
        this.userId = userId;
        this.productId = productId;
    }
 
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass()) 
            return false;
 
        UserProductId that = (UserProductId) o;
        return Objects.equals(userId, that.userId) && 
               Objects.equals(productId, that.productId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
}