package com.example.wizshopjava.api.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wizshopjava.api.jwtauth.dto.RequestMember;
import com.example.wizshopjava.api.product.dto.ProductCreateRequest;
import com.example.wizshopjava.api.product.dto.ProductCreateResponse;
import com.example.wizshopjava.api.product.dto.ProductDetailResponse;
import com.example.wizshopjava.api.product.dto.ProductTitleResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ProductCreateResponse> registerProduct(
		RequestMember member,
		@RequestBody ProductCreateRequest req) {
		ProductCreateResponse response = productService.create(member.id(), req);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
		productService.delete(productId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductDetailResponse> retrieve(@PathVariable Long productId) {
		ProductDetailResponse response = productService.retrieve(productId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<ProductTitleResponse>> search(
		@RequestParam String keyword,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size) {
		PageRequest pageable = PageRequest.of(page - 1, size);
		Page<ProductTitleResponse> response = productService.search(keyword, pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/popular")
	public ResponseEntity<List<String>> retrievePopularTop5() {
		List<String> response = productService.retrievePopularTop5();
		return ResponseEntity.ok(response);
	}

}
