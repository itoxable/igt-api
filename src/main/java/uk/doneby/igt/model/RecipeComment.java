package uk.doneby.igt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="RECIPE_COMMENT")
public class RecipeComment extends BaseModel implements Serializable {

	private static final long serialVersionUID = 2935833796581096149L;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="COMMENT", nullable = false)
	private String comment;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RECIPE_ID")
	private Recipe recipe;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

}
