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
		collectionResourceRel = "nutrition",
		path = "nutrition",
		itemResourceRel="nutrition")
public interface NutritionalInfoRepository extends PagingAndSortingRepository<NutritionalInfo, Long> {
		
	public List<NutritionalInfo> findByNameIn(@Param("names") List<String> names);
	
	
}