package uk.doneby.igt.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="USER_RECIPE")
public class UserRecipe implements Serializable {

	private static final long serialVersionUID = -750641487222268695L;

	@EmbeddedId
    private UserRecipeId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
	private User user;
		
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
	private Recipe recipe;
	
	@Column(name = "CDATE", nullable = false)
	private Date cdate;

	@Column(name = "MDATE", nullable = false)
	private Date mdate;
	
	private UserRecipe() {}
	
	public UserRecipe(User user, Recipe recipe) {
		this.user = user;
		this.recipe = recipe;
		id = new UserRecipeId(user.getId(), recipe.getId());

	}
	
	@PrePersist
	void onCreate() {
		Date now = new Date();
		this.setCdate(now);
		this.setMdate(now);
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}


	public Date getCdate() {
		return cdate;
	}


	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}


	public Date getMdate() {
		return mdate;
	}


	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}

}
