package uk.doneby.igt.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RecipeProductId implements Serializable {
 
	private static final long serialVersionUID = 8739833427479524227L;

	@Column(name = "RECIPE_ID")
    private Long recipeId;
 
    @Column(name = "PRODUCT_ID")
    private Long productId;
 
    private RecipeProductId() {}
 
    public RecipeProductId(Long recipeId,  Long productId) {
        this.recipeId = recipeId;
        this.productId = productId;
    }
 
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass()) 
            return false;
 
        RecipeProductId that = (RecipeProductId) o;
        return Objects.equals(recipeId, that.recipeId) && 
               Objects.equals(productId, that.productId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(recipeId, productId);
    }


	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}
}