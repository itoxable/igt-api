package uk.doneby.igt.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import uk.doneby.igt.model.User;
import uk.doneby.igt.model.UserRecipe;
import uk.doneby.igt.model.UserRecipeId;

@RepositoryRestResource(
		collectionResourceRel = "user-recipes",
		path = "user-recipes",
		itemResourceRel="user-recipes")
public interface UserRecipeRepository extends PagingAndSortingRepository<UserRecipe, UserRecipeId> {
	
	List<UserRecipe> findByUser(User user);
	
}
