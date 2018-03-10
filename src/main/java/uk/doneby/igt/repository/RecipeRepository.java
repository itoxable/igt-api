package uk.doneby.igt.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import uk.doneby.igt.model.Recipe;
import uk.doneby.igt.model.User;
import uk.doneby.igt.model.UserRecipe;

@RepositoryRestResource(
		collectionResourceRel = "recipes",
		path = "recipes",
		itemResourceRel="recipe")
public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {
	
	@Query("SELECT r.recipe FROM UserRecipe r where r.user = ?1")
	public List<Recipe> findByUser(User user);

	@Query("SELECT r FROM Recipe r where r.featured = true")
	List<Recipe> findByFeatured();

}
