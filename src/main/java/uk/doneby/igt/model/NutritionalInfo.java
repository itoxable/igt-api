package uk.doneby.igt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="NUTRITIONAL_INFO")
public class NutritionalInfo extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = -3019131035171401208L;

	@Column(name="NAME", nullable = false, unique = true)
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="VALUE")
	private String value;
	
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
