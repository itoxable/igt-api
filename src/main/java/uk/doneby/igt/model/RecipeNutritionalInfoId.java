package uk.doneby.igt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RecipeNutritionalInfoId implements Serializable {
 
	private static final long serialVersionUID = -6561024751983563023L;
	

	@Column(name = "RECIPE_ID")
    private Long recipeId;
 
    @Column(name = "NUTRITION_ID")
    private Long nutritionId;
    
      
    private RecipeNutritionalInfoId() {}
 
    public RecipeNutritionalInfoId(Long recipeId, Long nutritionId) {
        this.recipeId = recipeId;
        this.nutritionId = nutritionId;
    }
    
	public Long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}

	public Long getNutritionId() {
		return nutritionId;
	}

	public void setNutritionId(Long nutritionId) {
		this.nutritionId = nutritionId;
	}

}