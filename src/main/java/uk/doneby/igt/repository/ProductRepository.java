package uk.doneby.igt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import uk.doneby.igt.model.Product;
import uk.doneby.igt.model.User;

@RepositoryRestResource(
		collectionResourceRel = "product-repo",
		path = "product-repo",
		itemResourceRel="product-repo")
public interface ProductRepository extends CrudRepository<Product, Long>  {
	
	@Query("SELECT r.product FROM UserProduct r where r.user = ?1")
	public List<Product> findByUser(User user);
	
	@Query("SELECT r.product FROM UserProduct r where r.user.id = :userId")
	public List<Product> findByUserId(@Param("userId") Long userId);
	
	//@RestResource(exported=true)
//	@Query("SELECT COUNT(b) FROM BasicLandAndPropertyUnit b WHERE b.parentUprn=:parentUprn GROUP BY b.parentUprn")
//	Long countByParentUprn(@Param("parentUprn") BigDecimal parentUprn);
	
}