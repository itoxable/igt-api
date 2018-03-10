package uk.doneby.igt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="PRODUCT")
public class Product extends BaseModel implements Serializable {

	private static final long serialVersionUID = 7971108680024557857L;
	
	@Column(name="NAME", nullable = false)
	private String name;
	
	@Column(name="IMAGE")
	private String image;
	
	@Column(name="DESCRIPTION")
	private String description;

	@OneToMany(
        mappedBy = "recipe",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<RecipeProduct> recipeProduct = new ArrayList<RecipeProduct>();
	

	@Transient
	private String quantity = "1";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<RecipeProduct> getRecipeProduct() {
		return recipeProduct;
	}

	public void setRecipeProduct(List<RecipeProduct> recipeProduct) {
		this.recipeProduct = recipeProduct;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
}
