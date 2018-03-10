package uk.doneby.igt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="RECIPE_PRODUCT")
public class RecipeProduct implements Serializable {

	private static final long serialVersionUID = -750641487222268695L;

	@EmbeddedId
    private RecipeProductId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
	private Recipe recipe;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
	private Product product;

	@Column(name="QUANTITY")
	private Long quantity;
	
	public RecipeProduct(Recipe recipe, Product product, Long quantity) {
		this.product = product;
		this.recipe = recipe;
		this.quantity = quantity;
	}
	
	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

}
