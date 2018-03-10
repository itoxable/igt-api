package uk.doneby.igt.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import uk.doneby.igt.model.User;
import uk.doneby.igt.model.UserProduct;
import uk.doneby.igt.model.UserProductId;

@RepositoryRestResource(
		collectionResourceRel = "user-product",
		path = "user-product",
		itemResourceRel="user-product")
public interface UserProductRepository extends PagingAndSortingRepository<UserProduct, UserProductId> {
	List<UserProduct> findByUser(User user);

}
