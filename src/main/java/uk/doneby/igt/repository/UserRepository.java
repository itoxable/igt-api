package uk.doneby.igt.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import uk.doneby.igt.model.User;


@RepositoryRestResource(
		collectionResourceRel = "users",
		path = "users",
		itemResourceRel="user")
public interface UserRepository extends PagingAndSortingRepository<User, Long>  {
	
	User findByUsername(String username);
	User findByEmail(String email);

	
}
