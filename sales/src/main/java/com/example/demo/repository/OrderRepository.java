package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order;

/**
 * ユーザー情報 Repository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	@Query(value = "SELECT * FROM sales AS s WHERE s.title LIKE %:SeachOrder% ", nativeQuery = true) // SQL
	public Page<Order> findSeachAll(@Param("SeachOrder") String SeachOrder, Pageable pageable);
}
