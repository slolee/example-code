package com.example.wizshopjava.domain.product.entity;

import java.io.Serializable;

import com.example.wizshopjava.common.BaseEntity;
import com.example.wizshopjava.domain.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;

	private String name;
	private Long price;
	private String description;
	private Long hit = 0L;

	@Enumerated(value = EnumType.STRING)
	private ProductStatus status;

	private Long sellerId;

	@Builder
	public Product(Long id, String name, Long price, String description, ProductStatus status, Long sellerId) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.status = status;
		this.sellerId = sellerId;
	}

	public Product hit() {
		this.hit += 1;
		return this;
	}
}
