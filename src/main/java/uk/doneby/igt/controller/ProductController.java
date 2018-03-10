package uk.doneby.igt.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import uk.doneby.igt.model.Product;
import uk.doneby.igt.model.User;
import uk.doneby.igt.model.UserProduct;
import uk.doneby.igt.model.UserProductId;
import uk.doneby.igt.repository.ProductRepository;
import uk.doneby.igt.repository.UserProductRepository;

@Controller
@RequestMapping("/api/product")
public class ProductController {
	
	final static Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserProductRepository userProductRepository;
	
	
	@GetMapping("/user")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@ResponseBody
    public ResponseEntity<List<Product>> getUserProducts() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    	List<UserProduct> userProducts =  userProductRepository.findByUser(user);
//    	List<Product> products = new ArrayList<Product>();
//    	if (userProducts != null) {
//    		for(UserProduct userProduct : userProducts) {
//        		Product product = userProduct.getProduct();
//        		product.setQuantity(userProduct.getQuantity());
//        		products.add(product);
//        	}
//    	}
		List<Product> products = productRepository.findByUserId(user.getId());
    	return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Product> saveNewProduct(@RequestBody Product product) {
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	product = productRepository.save(product);
    	UserProduct userProduct = new UserProduct(user, product, product.getQuantity()); 
    	userProductRepository.save(userProduct);
    	return new ResponseEntity<Product>(product, HttpStatus.OK);// /api/product/add
    	
    }
	
	@DeleteMapping("/remove-user/{productId}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Boolean> removeUserProduct(@PathVariable("productId") Long productId) {
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	UserProductId id = new UserProductId(user.getId(), productId);
    	userProductRepository.delete(id);
    	return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
	
	@PutMapping("/save")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public Product updateProduct(@RequestBody Product product) {
//    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	product = productRepository.save(product);
    	return product;
    }
	
	@PostMapping("/associate-user")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public Product associateProduct(Product product) {
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();    	
    	UserProduct userProduct = new UserProduct(user, product, product.getQuantity()); 
    	userProductRepository.save(userProduct);
    	return product;
    }
	
}
