package uk.doneby.igt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DIET_TYPE")
public class DietType implements Serializable {

	private static final long serialVersionUID = 6615598067297329753L;

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;
	
	@Column(name="NAME", nullable = false, unique=true)
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name: '").append(name).append("', ")
		.append("description: '").append(description).append("', ")
		.append("id: '").append(id).append("', ")
		.append(super.toString());
		return sb.toString();
	}
	
}
