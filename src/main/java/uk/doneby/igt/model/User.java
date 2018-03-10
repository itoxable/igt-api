package uk.doneby.igt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name="USER")
public class User extends BaseModel implements UserDetails, Serializable{

	private static final long serialVersionUID = -4276067408950251800L;
	
	@Column(name="NAME", nullable = false)
	private String name;
	
	@Column(name="EMAIL", nullable = false, unique = true)
	private String email;
	
	@Column(name="USERNAME", nullable = false, unique = false)
	private String username;
	
	@Column(name="nutrition_Type")
	private String nutritionType;
	
	@Column(name="LAST_LOGIN")
	private Date lastLogin;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Transient
	private List<GrantedAuthority> authorities;
	
	@Transient
	private boolean accountNonExpired = true;
	
	@Transient
	private boolean accountNonLocked = true;
	
	@Transient
	private boolean credentialsNonExpired = true;
	
	@Transient
	private boolean enabled = true;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
//
//	public List<Product> getProducts() {
//		return products;
//	}
//
//	public void setProducts(List<Product> products) {
//		this.products = products;
//	}

	public String getNutritionType() {
		return nutritionType;
	}

	public void setNutritionType(String nutritionType) {
		this.nutritionType = nutritionType;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

//	public List<Recipe> getRecipes() {
//		return recipes;
//	}
//
//	public void setRecipes(List<Recipe> recipes) {
//		this.recipes = recipes;
//	}
}