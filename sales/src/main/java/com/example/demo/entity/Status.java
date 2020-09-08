package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

@Entity
@Data
@IdClass(StatusPK.class)
public class Status {
	/**
	 * 顧客id
	 * 複合キー1
	 * */
	@Id
	private int customerid;

	/**
	 * ステータスid
	 * 複合キー2
	 * */
	@Id
	private int statusid;

	/**
	 * ステータス名
	 * */
	private String status;
}
