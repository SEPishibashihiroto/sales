package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	@Query(value = "SELECT customerid ,customer.customername AS customer FROM customer", nativeQuery = true)
	public List<Customer> findCustomerAll();
}
