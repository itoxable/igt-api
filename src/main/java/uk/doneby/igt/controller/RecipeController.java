package uk.doneby.igt.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import uk.doneby.igt.model.Product;
import uk.doneby.igt.model.Recipe;
import uk.doneby.igt.model.User;
import uk.doneby.igt.model.UserProduct;
import uk.doneby.igt.model.UserProductId;
import uk.doneby.igt.model.UserRecipe;
import uk.doneby.igt.model.UserRecipeId;
import uk.doneby.igt.repository.RecipeRepository;
import uk.doneby.igt.repository.UserRecipeRepository;
import uk.doneby.igt.repository.UserRepository;


@Controller
@RequestMapping("/api/recipe")
public class RecipeController {
	
	final static Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private UserRecipeRepository userRecipeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping("/user")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Recipe> getUserRecipes() {
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	return recipeRepository.findByUser(user);
    }
	
	@GetMapping("/featured")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Recipe> getFeaturedRecipes() {
    	return recipeRepository.findByFeatured();
    }
	
	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Recipe>  saveNewRecipe(@RequestBody Recipe recipe) {
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	recipe = recipeRepository.save(recipe);
    	UserRecipe userRecipe = new UserRecipe(user, recipe); 
    	userRecipeRepository.save(userRecipe);
    	return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
    }
	
	@PutMapping("/save")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public Recipe updateRecipe(@RequestBody Recipe recipe) {
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	recipe = recipeRepository.save(recipe);
    	return recipe;
    }
	
	
	@DeleteMapping("/remove-user/{recipeId}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Boolean> removeUserRecipe(@PathVariable("recipeId") Long recipeId) {
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	UserRecipeId id = new UserRecipeId(user.getId(), recipeId);
    	userRecipeRepository.delete(id);
    	return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
	
}