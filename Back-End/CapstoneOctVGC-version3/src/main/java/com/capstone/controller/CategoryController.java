package com.capstone.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.model.Category;
import com.capstone.model.Product;
import com.capstone.repository.CategoryRepository;
import com.capstone.repository.ProductRepository;
import com.capstone.service.CategoryService;
import com.capstone.service.ProductService;

@RestController
@RequestMapping("/admin/categories")
public class CategoryController {
	@Autowired
	CategoryService catService;
	
	@Autowired
	ProductService prdService;
	
	@Autowired
	private ProductRepository prdRepo;
	
	@Autowired
	private CategoryRepository catRepo;

	@GetMapping("/all")
	public ResponseEntity<List<Category>> listOfAllCategories(){
		try {
			List<Category> allCategories = catService.listAllCategories();
			if(allCategories.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(allCategories, HttpStatus.OK);
			}
		} catch(Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/product_categories/{prdId}")
	public ResponseEntity<List<Category>> findCategoriesUnderProductId(@PathVariable Integer prdId){
		try {
			List<Category> categories = catRepo.findCategoriesByProductsId(prdId);
			return new ResponseEntity<>(categories, HttpStatus.OK);
		}catch (NoSuchElementException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Optional<Category>> findCategoriesById(@PathVariable Integer id){
		try {
			Optional<Category> categories = catService.findByCategoryId(id);
			return new ResponseEntity<>(categories, HttpStatus.OK);
		}catch (NoSuchElementException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/products_under_category/{catId}")
	public ResponseEntity<List<Product>> findProductsInCategory(@PathVariable Integer catId){
		try {
			List<Product> products = prdRepo.findProductsByCategoriesId(catId);
			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (NoSuchElementException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("add_to_product/{prdId}")
	public ResponseEntity<Category> addCategory(@PathVariable Integer prdId, @RequestBody Category catRequest){
		Category categ = prdRepo.findById(prdId).map(product -> {
			int catId = catRequest.getId();
			if (catId!=0) {
				Category category = catService.findByCategoryId(catId)
						.orElseThrow(()-> new NoSuchElementException("Category with id " +catId+" not found."));
				product.addCategory(category);
				prdRepo.save(product);
				return category;
			}
			product.addCategory(catRequest);
			return catRepo.save(catRequest);
		}).orElseThrow(()-> new NoSuchElementException("Product with id " +prdId+" not found."));
		return new ResponseEntity<>(categ, HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody Category cat){
		try {
			Category databaseCat = catService.findByCategoryId(id).get();
			if(Objects.nonNull(cat.getCategoryName())) {
				databaseCat.setCategoryName(cat.getCategoryName());
			} 
			return new ResponseEntity<>(catService.save(databaseCat), HttpStatus.OK);
		}catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/remove/{tagId}/from_product/{prdId}")
	public ResponseEntity<HttpStatus> deleteCategoryFromProduct(@PathVariable Integer tagId, @PathVariable Integer prdId){
		try {
			Product product = prdService.findByProductId(prdId).get();
			product.removeCategory(tagId);
			prdService.save(product);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("remove/{id}")
	public ResponseEntity<HttpStatus> deleteCategoryById(@PathVariable Integer id){
		try {
			catService.deleteCategoryById(id);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
