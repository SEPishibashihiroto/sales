package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
	@Query(value = "SELECT customerid ,statusid, status.statusname AS status FROM status", nativeQuery = true)
	public List<Status> findStatusAll();
}
