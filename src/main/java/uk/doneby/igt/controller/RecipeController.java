package uk.doneby.igt.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import uk.doneby.igt.model.Product;
import uk.doneby.igt.model.Recipe;
import uk.doneby.igt.model.RecipeProduct;
import uk.doneby.igt.model.User;
import uk.doneby.igt.model.UserProduct;
import uk.doneby.igt.model.UserProductId;
import uk.doneby.igt.model.UserRecipe;
import uk.doneby.igt.model.UserRecipeId;
import uk.doneby.igt.repository.ProductRepository;
import uk.doneby.igt.repository.RecipeProductRepository;
import uk.doneby.igt.repository.RecipeRepository;
import uk.doneby.igt.repository.UserRecipeRepository;
import uk.doneby.igt.repository.UserRepository;
import uk.doneby.igt.service.StorageService;


@Controller
@RequestMapping("/api/recipe")
public class RecipeController {
	
	final static Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private UserRecipeRepository userRecipeRepository;
	
	@Autowired
	private RecipeProductRepository recipeProductRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private StorageService storageService;
	
	@GetMapping("/user")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Recipe>> getUserRecipes() {
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();  	
    	List<Recipe> recipes = recipeRepository.findByUser(user);
		if (recipes == null) {
			recipes = new ArrayList<Recipe>();
		}
		return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);

    }
	
	@GetMapping("/featured")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Recipe> getFeaturedRecipes() {
		List<Recipe> recipes = recipeRepository.findByFeatured();
		if (recipes == null) {
			recipes = new ArrayList<Recipe>();
		}
    	return recipes;
    }
	
	@PostMapping("/add-image")
    public ResponseEntity<String> saveImage(MultipartFile image) throws IOException {
		String filename = storageService.store(image);
    	System.out.println(filename);
		return new ResponseEntity<String>(filename, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Recipe> saveNewRecipe(@RequestBody Recipe recipe) {
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	List<Product> products = recipe.getProducts();
    	recipe = recipeRepository.save(recipe);
    	UserRecipe userRecipe = new UserRecipe(user, recipe); 
    	userRecipeRepository.save(userRecipe);
    	
    	for(Product product: products) {
    		Product existingProduct = productRepository.findByName(product.getName());
    		if(existingProduct == null) {
    			product = productRepository.save(product);
    		}
    		RecipeProduct recipeProduct = new RecipeProduct(recipe, product, product.getQuantity());
    		recipeProductRepository.save(recipeProduct);
    	}
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