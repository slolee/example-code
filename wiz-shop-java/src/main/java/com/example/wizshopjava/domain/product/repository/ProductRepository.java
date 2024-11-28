package com.example.wizshopjava.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.wizshopjava.domain.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	// TODO : QueryDSL 로 수정
	@Query(
		value = "SELECT p FROM Product p WHERE p.name LIKE concat('%', :keyword, '%') OR p.description LIKE concat('%', :keyword, '%')",
		countQuery = "SELECT count(p) FROM Product p WHERE p.name LIKE concat('%', :keyword, '%') OR p.description LIKE concat('%', :keyword, '%')"
	)
	Page<Product> searchByKeyword(String keyword, PageRequest page);
}
