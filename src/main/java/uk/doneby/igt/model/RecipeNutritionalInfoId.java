package uk.doneby.igt.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RecipeNutritionalInfoId implements Serializable {
 
	private static final long serialVersionUID = -6561024751983563023L;
	
	@Column(name = "RECIPE_ID")
    private Long recipeId;
 
    @Column(name = "NUTRITIONAL_INFO_ID")
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
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass()) 
            return false;
 
        RecipeNutritionalInfoId that = (RecipeNutritionalInfoId) o;
        return Objects.equals(recipeId, that.recipeId) && 
               Objects.equals(nutritionId, that.nutritionId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(recipeId, nutritionId);
    }

}