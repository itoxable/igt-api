package uk.doneby.igt.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="PRODUCT")
public class Product extends BaseModel implements Serializable {

	private static final long serialVersionUID = 7971108680024557857L;
	
	@Column(name="NAME", nullable = false, unique=true)
	private String name;
	
	@Column(name="IMAGE")
	private String image;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="BARCODE")
	private String barCode;

	@Transient
	private String quantity = "1";
	
	@Transient
	private Date bestBeforeDate;

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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Date getBestBeforeDate() {
		return bestBeforeDate;
	}

	public void setBestBeforeDate(Date bestBeforeDate) {
		this.bestBeforeDate = bestBeforeDate;
	}
	
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name: '").append(name).append("', ")
		.append("image: '").append(image).append("', ")
		.append("quantity: '").append(quantity).append("', ")
		.append("bestBeforeDate: '").append(bestBeforeDate).append("', ")
		.append(super.toString());
		return sb.toString();
	}
	
}
