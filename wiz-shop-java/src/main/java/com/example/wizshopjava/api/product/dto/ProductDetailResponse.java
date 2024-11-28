package com.example.wizshopjava.api.product.dto;

import java.time.LocalDateTime;

import com.example.wizshopjava.domain.product.entity.Product;
import com.example.wizshopjava.domain.product.entity.ProductStatus;

public record ProductDetailResponse(
	String productName,
	Long price,
	String description,
	Long hit,
	ProductStatus status,
	LocalDateTime createdAt
) {

	public static ProductDetailResponse from(Product product) {
		return new ProductDetailResponse(
			product.getName(),
			product.getPrice(),
			product.getDescription(),
			product.getHit(),
			product.getStatus(),
			product.getCreatedAt()
		);
	}
}
