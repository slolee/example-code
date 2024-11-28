package com.example.wizshopjava.api.product;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.wizshopjava.api.product.dto.ProductCreateRequest;
import com.example.wizshopjava.api.product.dto.ProductCreateResponse;
import com.example.wizshopjava.api.product.dto.ProductDetailResponse;
import com.example.wizshopjava.api.product.dto.ProductTitleResponse;
import com.example.wizshopjava.domain.product.entity.Product;
import com.example.wizshopjava.domain.product.repository.PopularSearchKeywordRedisRepository;
import com.example.wizshopjava.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final PopularSearchKeywordRedisRepository popularSearchKeywordRedisRepository;

	@CacheEvict(cacheNames = {"PRODUCT_SEARCH"}, allEntries = true)
	public ProductCreateResponse create(Long memberId, ProductCreateRequest req) {
		Product product = productRepository.save(req.toEntity(memberId));
		return new ProductCreateResponse(product.getId());
	}

	@CacheEvict(cacheNames = {"PRODUCT_SEARCH"}, allEntries = true)
	public void delete(Long productId) {
		productRepository.deleteById(productId);
	}

	@Transactional
	public ProductDetailResponse retrieve(Long productId) {
		Product product = productRepository.findById(productId).orElseThrow();
		product.hit();
		return ProductDetailResponse.from(product);
	}

	// PRODUCT_SEARCH::test1
	@Cacheable(cacheManager = "productSearchCacheManager", key = "#keyword", value = "PRODUCT_SEARCH")
	public Page<ProductTitleResponse> search(String keyword, PageRequest pageable) {
		if (pageable.getPageNumber() == 0) {
			popularSearchKeywordRedisRepository.increment(keyword);
		}
		return productRepository.searchByKeyword(keyword, pageable)
			.map(ProductTitleResponse::from);
	}

	public List<String> retrievePopularTop5() {
		return popularSearchKeywordRedisRepository.findTopN(5);
	}
}

