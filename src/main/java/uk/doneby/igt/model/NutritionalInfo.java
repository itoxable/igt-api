package uk.doneby.igt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="NUTRITIONAL_INFO")
public class NutritionalInfo implements Serializable {
	
	private static final long serialVersionUID = -3019131035171401208L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@Column(name="NAME", nullable = false, unique = true)
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Transient
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
