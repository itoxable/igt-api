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
	private String quantity;
	
	private RecipeProduct() {}
	
	public RecipeProduct(Recipe recipe, Product product, String quantity) {
		this.product = product;
		this.recipe = recipe;
		this.quantity = quantity;
		id = new RecipeProductId(recipe.getId(), product.getId());
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
