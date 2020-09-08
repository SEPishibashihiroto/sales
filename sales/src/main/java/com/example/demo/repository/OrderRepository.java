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
	//検索条件あり
	@Query(value = "SELECT sales.id,customer.customerid,customer.customername AS customer,sales.orderdate,sales.snumber,sales.title,sales.count,sales.specifieddate,"
			+
			"sales.deliverydate,sales.billingdate,sales.quoteprice,sales.orderprice,status.statusid,status.statusname AS status,sales.delete_flg "
			+
			"FROM sales ,customer ,status " +
			"WHERE sales.customerid = customer.customerid " +
			"AND sales.customerid = status.customerid " +
			"AND sales.statusid = status.statusid " +
			"AND sales.customerid LIKE %:SeachCustomer% " +
			"AND sales.title LIKE %:SeachTitle% " +
			"AND sales.statusid LIKE %:SeachStatus% " +
			"AND sales.delete_flg = 0", nativeQuery = true) // SQL
	public Page<Order> findSeachAll(@Param("SeachCustomer") String SeachCustomer,
			@Param("SeachTitle") String SeachTitle, @Param("SeachStatus") String SeachStatus, Pageable pageable);

	//検索条件なし
	@Query(value = "SELECT sales.id,customer.customerid,customer.customername AS customer,sales.orderdate,sales.snumber,sales.title,sales.count,sales.specifieddate,"
			+
			"sales.deliverydate,sales.billingdate,sales.quoteprice,sales.orderprice,status.statusid,status.statusname AS status,sales.delete_flg "
			+
			"FROM sales ,customer ,status " +
			"WHERE sales.customerid = customer.customerid " +
			"AND sales.customerid = status.customerid " +
			"AND sales.statusid = status.statusid " +
			"AND sales.delete_flg = 0", nativeQuery = true) // SQL
	public Page<Order> findSeachAll(Pageable pageable);


}
