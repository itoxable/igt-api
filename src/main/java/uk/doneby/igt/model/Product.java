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
	
	@Column(name="CALORIES")
	private String calories;
	
	@Column(name="CARBS")
	private String carbs;
	
	@Column(name="UNSATURATED_FAT")
	private String unsaturatedFat;
	
	@Column(name="SATURATED_FAT")
	private String saturatedFat;
	
	@Column(name="polyunsaturated_Fat")
	private String polyunsaturatedFat;
	
	@Column(name="Trans_Fat")
	private String transFat;
	
	@Column(name="proteins")
	private String proteins;
	
	@Column(name="vitamins")
	private String vitamins;
	
	@Transient
	private Long quantity = 1L;

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


	public String getCalories() {
		return calories;
	}

	public void setCalories(String calories) {
		this.calories = calories;
	}

	public String getCarbs() {
		return carbs;
	}

	public void setCarbs(String carbs) {
		this.carbs = carbs;
	}

	public String getUnsaturatedFat() {
		return unsaturatedFat;
	}

	public void setUnsaturatedFat(String unsaturatedFat) {
		this.unsaturatedFat = unsaturatedFat;
	}

	public String getSaturatedFat() {
		return saturatedFat;
	}

	public void setSaturatedFat(String saturatedFat) {
		this.saturatedFat = saturatedFat;
	}

	public String getPolyunsaturatedFat() {
		return polyunsaturatedFat;
	}

	public void setPolyunsaturatedFat(String polyunsaturatedFat) {
		this.polyunsaturatedFat = polyunsaturatedFat;
	}

	public String getTransFat() {
		return transFat;
	}

	public void setTransFat(String transFat) {
		this.transFat = transFat;
	}

	public String getProteins() {
		return proteins;
	}

	public void setProteins(String proteins) {
		this.proteins = proteins;
	}

	public String getVitamins() {
		return vitamins;
	}

	public void setVitamins(String vitamins) {
		this.vitamins = vitamins;
	}

	public List<RecipeProduct> getRecipeProduct() {
		return recipeProduct;
	}

	public void setRecipeProduct(List<RecipeProduct> recipeProduct) {
		this.recipeProduct = recipeProduct;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
}
