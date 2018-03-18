package uk.doneby.igt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import uk.doneby.igt.model.Recipe;
import uk.doneby.igt.model.RecipeProduct;
import uk.doneby.igt.model.User;


@RepositoryRestResource(
		collectionResourceRel = "recipe-product",
		path = "recipe-product",
		itemResourceRel="recipe-product")
public interface RecipeProductRepository extends PagingAndSortingRepository<RecipeProduct, Long> {
	
	@Query("SELECT r.recipe FROM UserRecipe r where r.user = ?1")
	public List<Recipe> findByUser(User user);

	@Query("SELECT r FROM Recipe r where r.featured = true")
	List<Recipe> findByFeatured();

}