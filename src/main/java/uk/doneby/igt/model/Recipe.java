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
	private int preparationTime;
	
	@Column(name="CALORIES")
	private String calories;
	
	@Column(name="CARBS")
	private String carbs;
	
	@Column(name="UNSATURATED_FAT")
	private String unsaturatedFat;
	
	@Column(name="SATURATED_FAT")
	private String saturatedFat;
	
	@Column(name="POLYUNSATURATED_FAT")
	private String polyunsaturatedFat;
	
	@Column(name="TRANS_FAT")
	private String transFat;
	
	@Column(name="PROTEINS")
	private String proteins;
	
	@Column(name="VITAMINS")
	private String vitamins;
	
	@Column(name="FEATURED")
	private boolean featured;
	
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

	public int getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}

	public String getCalories() {
		return calories;
	}

	public void setCalories(String calories) {
		this.calories = calories;
	}

	public String getCarbs() {
		return carbs;
	}

	public void setCarbs(String carbs) {
		this.carbs = carbs;
	}

	public String getUnsaturatedFat() {
		return unsaturatedFat;
	}

	public void setUnsaturatedFat(String unsaturatedFat) {
		this.unsaturatedFat = unsaturatedFat;
	}

	public String getSaturatedFat() {
		return saturatedFat;
	}

	public void setSaturatedFat(String saturatedFat) {
		this.saturatedFat = saturatedFat;
	}

	public String getPolyunsaturatedFat() {
		return polyunsaturatedFat;
	}

	public void setPolyunsaturatedFat(String polyunsaturatedFat) {
		this.polyunsaturatedFat = polyunsaturatedFat;
	}

	public String getTransFat() {
		return transFat;
	}

	public void setTransFat(String transFat) {
		this.transFat = transFat;
	}

	public String getProteins() {
		return proteins;
	}

	public void setProteins(String proteins) {
		this.proteins = proteins;
	}

	public String getVitamins() {
		return vitamins;
	}

	public void setVitamins(String vitamins) {
		this.vitamins = vitamins;
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

}
