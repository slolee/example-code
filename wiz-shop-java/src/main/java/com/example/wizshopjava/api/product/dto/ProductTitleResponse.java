package com.example.wizshopjava.api.product.dto;

import java.time.LocalDateTime;

import com.example.wizshopjava.domain.product.entity.Product;
import com.example.wizshopjava.domain.product.entity.ProductStatus;

public record ProductTitleResponse(
	Long productId,
	String productName,
	Long price,
	Long hit,
	ProductStatus status,
	LocalDateTime createdAt
) {
	public static ProductTitleResponse from(Product product) {
		return new ProductTitleResponse(
			product.getId(),
			product.getName(),
			product.getPrice(),
			product.getHit(),
			product.getStatus(),
			product.getCreatedAt()
		);
	}
}
