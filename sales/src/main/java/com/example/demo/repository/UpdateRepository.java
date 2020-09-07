package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Update;

@Repository
public interface UpdateRepository extends JpaRepository<Update, Integer> {
	@Query(value = "SELECT * FROM sales", nativeQuery = true)
	public List<Update> findStatusAll();
}
