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
@Table(name="RECIPE_NUTRITION")
public class RecipeNutritionalInfo implements Serializable {
	
	private static final long serialVersionUID = -6457282913677774704L;
	
	@EmbeddedId
    private RecipeNutritionalInfoId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
	private Recipe recipe;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
	private NutritionalInfo nutritionalInfo;
	
	@Column(name = "VALUE")
	private String value;
	
	private RecipeNutritionalInfo() {}
	
	public RecipeNutritionalInfo(Recipe recipe, NutritionalInfo nutritionalInfo, String value) {
		this.nutritionalInfo = nutritionalInfo;
		this.recipe = recipe;
		this.value = value;
		id = new RecipeNutritionalInfoId(recipe.getId(), nutritionalInfo.getId());
	}

	public RecipeNutritionalInfoId getId() {
		return id;
	}

	public void setId(RecipeNutritionalInfoId id) {
		this.id = id;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public NutritionalInfo getNutritionalInfo() {
		return nutritionalInfo;
	}

	public void setNutritionalInfo(NutritionalInfo nutritionalInfo) {
		this.nutritionalInfo = nutritionalInfo;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
