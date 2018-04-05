package uk.doneby.igt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import uk.doneby.igt.model.NutritionalInfo;
import uk.doneby.igt.model.RecipeNutritionalInfo;
import uk.doneby.igt.model.RecipeNutritionalInfoId;


@RepositoryRestResource(
		collectionResourceRel = "recipe-nutrition",
		path = "recipe-nutrition",
		itemResourceRel="recipe-nutrition")
public interface RecipeNutritionalInfoRepository extends PagingAndSortingRepository<RecipeNutritionalInfo, RecipeNutritionalInfoId> {
		
	@Query("SELECT rn.nutritionalInfo FROM RecipeNutritionalInfo rn where rn.id.recipeId = :recipeId")
	public List<NutritionalInfo> findByRecipeId(@Param("recipeId") Long recipeId);
	
	
}