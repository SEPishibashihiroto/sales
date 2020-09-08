package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Customer {
	/**
	 * 顧客id
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String customerid;

	/**
	 * 顧客名
	 * */
	private String customer;
}
