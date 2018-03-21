package uk.doneby.igt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;


@Entity
@Table(name="RECIPE")
public class Recipe extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = -5010663769124667099L;

	@Column(name="NAME", nullable = false)
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="IMAGE")
	private String image;
		
	@Column(name="DIRECTIONS")
	private String directions;
	
	@Column(name="DIET_TYPE")
	private DietType dietType;
	
	@Column(name="PREPARATION_TIME")
	private Long preparationTime;
	
	@Column(name="SERVINGS")
	private int servings;
	
	@Transient
	private List<NutritionalInfo> nutritionalInfo;
		
	@Column(name="FEATURED")
	private boolean featured;
	
	@Column(name="LIKES")
	private Long likes;
	
	@Column(name="VIEWS")
	private Long views;
	
	@Transient
	private MultipartFile imageFile;
	
	@Transient
	private List<Product> products;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	public DietType getDietType() {
		return dietType;
	}

	public void setDietType(DietType dietType) {
		this.dietType = dietType;
	}

	public Long getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(Long preparationTime) {
		this.preparationTime = preparationTime;
	}

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}


	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name: '").append(name).append("', ")
		.append("description: '").append(description).append("', ")
		.append(super.toString());
		return sb.toString();
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public int getServings() {
		return servings;
	}

	public void setServings(int servings) {
		this.servings = servings;
	}

	public List<NutritionalInfo> getNutritionalInfo() {
		return nutritionalInfo;
	}

	public void setNutritionalInfo(List<NutritionalInfo> nutritionalInfo) {
		this.nutritionalInfo = nutritionalInfo;
	}

}
