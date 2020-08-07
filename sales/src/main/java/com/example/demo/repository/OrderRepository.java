package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order2;

/**
 * ユーザー情報 Repository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order2, Integer> {
	@Query(value = "SELECT id,customername AS customer,orderdate,snumber,title,count,specifieddate,deliverydate,billingdate,quoteprice,orderprice,statusname AS status "
			+ "FROM sales s " +
			"JOIN customer c ON s.customerid = c.customerid " +
			"JOIN status st ON s.customerid = st.customerid " +
			"AND s.statusid = st.statusid " +
			"WHERE s.customerid LIKE %:SeachCustomer% "
			+ "AND s.title LIKE %:SeachTitle% "
			+ "AND s.statusid LIKE %:SeachStatus% ", nativeQuery = true) // SQL
	public Page<Order2> findSeachAll(@Param("SeachCustomer") String SeachCustomer,
			@Param("SeachTitle") String SeachTitle, @Param("SeachStatus") String SeachStatus, Pageable pageable);
}
