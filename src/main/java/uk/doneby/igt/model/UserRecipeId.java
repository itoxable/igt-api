package uk.doneby.igt.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class UserRecipeId implements Serializable {
 
	private static final long serialVersionUID = 9065828885461127128L;

	@Column(name = "USER_ID")
    private Long userId;
 
    @Column(name = "RECIPE_ID")
    private Long recipeId;
 
    private UserRecipeId() {}
 
    public UserRecipeId(Long userId,  Long recipeId) {
        this.userId = userId;
        this.recipeId = recipeId;
    }
 
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass()) 
            return false;
 
        UserRecipeId that = (UserRecipeId) o;
        return Objects.equals(userId, that.recipeId) && 
               Objects.equals(recipeId, that.recipeId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(userId, recipeId);
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}
}