package uk.doneby.igt.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;

import uk.doneby.igt.model.NutritionalInfo;
import uk.doneby.igt.model.Product;
import uk.doneby.igt.model.Recipe;
import uk.doneby.igt.model.RecipeNutritionalInfo;
import uk.doneby.igt.model.RecipeProduct;
import uk.doneby.igt.model.User;
import uk.doneby.igt.model.UserRecipe;
import uk.doneby.igt.model.UserRecipeId;
import uk.doneby.igt.repository.NutritionalInfoRepository;
import uk.doneby.igt.repository.ProductRepository;
import uk.doneby.igt.repository.RecipeNutritionalInfoRepository;
import uk.doneby.igt.repository.RecipeProductRepository;
import uk.doneby.igt.repository.RecipeRepository;
import uk.doneby.igt.repository.UserRecipeRepository;
import uk.doneby.igt.service.RecipeService;
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
	private RecipeNutritionalInfoRepository recipeNutritionalInfoRepository;
	
	@Autowired
	private NutritionalInfoRepository nutritionalInfoRepository;
	
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private RecipeService recipeService;
	
	
	@GetMapping("/user")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Recipe>> getUserRecipes() {
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();  	
    	List<Recipe> recipes = recipeRepository.findByUser(user);
		if (recipes == null) {
			recipes = new ArrayList<Recipe>();
		} else {
			recipes.stream().forEach(recipe -> getNuritionalInfo(recipe));
		}
		return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
    }
	
	private void getNuritionalInfo(Recipe recipe) {
		List<NutritionalInfo> info = recipeNutritionalInfoRepository.findByRecipeId(recipe.getId());
		recipe.setNutritionalInfo(info);
	}
	
	@GetMapping("/nutrition-list")
    public ResponseEntity<List<NutritionalInfo>> getAllNutritionalInfo() {
		List<NutritionalInfo> nutritionalInfo = new ArrayList<NutritionalInfo>();
		nutritionalInfoRepository.findAll().forEach(nutritionalInfo::add);
		
		return new ResponseEntity<List<NutritionalInfo>>(nutritionalInfo, HttpStatus.OK);
    }
	
	@GetMapping("/featured")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Recipe>> getFeaturedRecipes() {
		List<Recipe> recipes = recipeRepository.findByFeatured();
		if (recipes == null) {
			recipes = new ArrayList<Recipe>();
		} else {
			recipes.stream().forEach(recipe -> getNuritionalInfo(recipe));
		}
		return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
    }
	
	@PostMapping("/add-image")
    public ResponseEntity<String> saveImage(MultipartFile image) throws IOException {
		String filename = storageService.store(image);
		return new ResponseEntity<String>(filename, HttpStatus.OK);
	}
	

	@PostMapping("/like")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Boolean> like(@RequestBody Long recipeId) {
		System.out.println("***RECIPE_ID: " + recipeId);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean result = recipeService.like(recipeId, user.getId());
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@GetMapping("/{recipeId}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable("recipeId") Long recipeId) {
		Recipe recipe = recipeRepository.findOne(recipeId);
		recipe.setProducts(recipeProductRepository.getRecipeProducts(recipe.getId()));
		return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
	}
	
	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Recipe> saveNewRecipe(@RequestBody Recipe recipe) {
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	List<Product> products = recipe.getProducts();
    	
    	List<NutritionalInfo> nutritionalInfoList = recipe.getNutritionalInfo();
    	Map<String, NutritionalInfo> nutritionalInfoMap = new HashMap<String, NutritionalInfo>();
    	nutritionalInfoList.stream().forEach(nutrInfo ->  nutritionalInfoMap.put(nutrInfo.getName(), nutrInfo));
    	
//    	recipe.setCreatedBy(user.getId());
//    	recipe.setModifiedByUser(user.getId());
    	recipe = recipeRepository.save(recipe);
    	UserRecipe userRecipe = new UserRecipe(user, recipe); 
    	userRecipeRepository.save(userRecipe);
    	
    	for(Product product: products) {
    		Product existingProduct = productRepository.findByName(product.getName());
    		if(existingProduct == null) {
    			existingProduct = productRepository.save(product);
    		}
    		RecipeProduct recipeProduct = new RecipeProduct(recipe, existingProduct, product.getQuantity());
    		recipeProductRepository.save(recipeProduct);
    	}
    
    	List<String> nutritionalInfoNames = new ArrayList<String>();
    	nutritionalInfoMap.keySet().forEach(nutritionalInfoNames::add);
    	
    	nutritionalInfoList = nutritionalInfoRepository.findByNameIn(nutritionalInfoNames);
    	List<RecipeNutritionalInfo> recipeNutritionalInfoList = new ArrayList<RecipeNutritionalInfo>();
    	for(NutritionalInfo nutritionalInfo: nutritionalInfoList) {
    		NutritionalInfo nf = nutritionalInfoMap.get(nutritionalInfo.getName());
    		recipeNutritionalInfoList.add(new RecipeNutritionalInfo(recipe, nf, nf.getValue()));
    	}
    	
    	recipeNutritionalInfoRepository.save(recipeNutritionalInfoList);
    	
    	
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