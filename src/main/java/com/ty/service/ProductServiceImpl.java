package com.ty.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.dto.ProductDto;
import com.ty.exception.CategoryNotFound;
import com.ty.exception.ProductNotFound;
import com.ty.model.Category;
import com.ty.model.Product;
import com.ty.repository.CategoryRepository;
import com.ty.repository.ProductRepository;
import com.ty.responsestructure.ResponseStructure;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository pRepo;

	@Autowired
	private CategoryRepository cRepo;

	@Override
	public ResponseEntity<?> getAllProducts(Integer pageNumber) {

	    if (pageNumber == null || pageNumber < 1) {
	        pageNumber = 1;
	    }

	    Pageable page = PageRequest.of(pageNumber - 1, 2);
	    Page<Product> allProducts = pRepo.findAll(page);

	    if (pageNumber > allProducts.getTotalPages() || allProducts.isEmpty()) {
	        ResponseStructure<String> rs = new ResponseStructure<>();
	        rs.setStatusCode(HttpStatus.NOT_FOUND.value());
	        rs.setMessage("Invalid page number or no products available.");
	        rs.setData("Page number: " + pageNumber);
	        return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);
	    }

	    ResponseStructure<List<Product>> rs = new ResponseStructure<>();
	    rs.setStatusCode(HttpStatus.OK.value());
	    rs.setMessage("All Products fetched successfully.");
	    rs.setData(allProducts.getContent());

	    return new ResponseEntity<>(rs, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<?> createProduct(Integer cid, Product product) {
		Category getCategory = cRepo.findById(cid)
				.orElseThrow(() -> new CategoryNotFound("Please add Category first."));

		Optional<Product> getProduct = pRepo.findByName(product.getName());

		if (getProduct.isPresent()) {
			ResponseStructure<String> rs = new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
			rs.setMessage("Product already present.");
			rs.setData("Cannot add product: " + product.getName());

			return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
		}

		product.setCategory(getCategory);
		Product newProduct = pRepo.save(product);

		
		ResponseStructure<Product> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setMessage("New product added successfully.");
		rs.setData(newProduct);

		return new ResponseEntity<>(rs, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> getProductById(Integer id) {
		// TODO Auto-generated method stub
		Product getProduct = pRepo.findById(id)
				.orElseThrow(() -> new ProductNotFound("Product not found with id: " + id));

		ProductDto dto = new ProductDto();
		BeanUtils.copyProperties(getProduct, dto);
		
		ResponseStructure<ProductDto> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Product Found.");
		rs.setData(dto);

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateProductById(Integer id, Product product) {
		// TODO Auto-generated method stub

		Product getProduct = pRepo.findById(id)
				.orElseThrow(() -> new ProductNotFound("Product not found with id: " + id));

		if (product.getName() != null) {
			getProduct.setName(product.getName());
		}
		if (product.getDescription() != null) {
			getProduct.setDescription(product.getDescription());
		}
		if (product.getPrice() != null) {
			getProduct.setPrice(product.getPrice());
		}

		Product updateProduct = pRepo.save(getProduct);

		ProductDto dto = new ProductDto();
		BeanUtils.copyProperties(updateProduct, dto);
		
		ResponseStructure<ProductDto> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Product Updated Successfully.");
		rs.setData(dto);

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteProductById(Integer id) {
		// TODO Auto-generated method stub
		  pRepo.findById(id)
				.orElseThrow(() -> new ProductNotFound("Product not found with id: " + id));

		pRepo.deleteById(id);

		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Product Deleted successfully.");
		rs.setData("Product deleted with id: " + id);

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

}
