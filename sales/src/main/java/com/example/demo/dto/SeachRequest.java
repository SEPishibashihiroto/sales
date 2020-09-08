package com.example.demo.dto;

import lombok.Data;

@Data
public class SeachRequest {

	/**
	 * 件名の検索対象
	 * */
	private String SeachTitle;

	/**
	 * ステータスの検索対象
	 * ステータスidで検索をかける
	 * */
	private String SeachStatus;

	/**
	 * 顧客の検索対象
	 * 顧客idで検索をかける
	 * */
	private String SeachCustomer;
}
