package uk.doneby.igt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import uk.doneby.igt.model.Product;
import uk.doneby.igt.model.User;
import uk.doneby.igt.model.UserProduct;
import uk.doneby.igt.model.UserProductId;

@RepositoryRestResource(
		collectionResourceRel = "user-product-repo",
		path = "user-product-repo",
		itemResourceRel="user-product-repo")
public interface UserProductRepository extends PagingAndSortingRepository<UserProduct, UserProductId> {
	List<UserProduct> findByUser(User user);
	
	@Query("SELECT r FROM UserProduct r where r.user.id = :userId")
	public List<UserProduct> findByUserId(@Param("userId") Long userId);
	
	@Query("SELECT r.product FROM UserProduct r where r.id.productId = :productId and r.id.userId = :userId")
	public List<Product> findOne(@Param("userId") Long userId, @Param("productId") Long productId);

}
