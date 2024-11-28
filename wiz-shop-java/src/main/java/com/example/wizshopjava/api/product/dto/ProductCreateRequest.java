package com.example.wizshopjava.api.product.dto;

import com.example.wizshopjava.domain.product.entity.Product;

public record ProductCreateRequest(
	String productName,
	Long price,
	String description
) {
	public Product toEntity(Long sellerId) {
		return Product.builder()
			.name(productName)
			.price(price)
			.description(description)
			.sellerId(sellerId)
			.build();
	}
}
