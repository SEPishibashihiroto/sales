package com.example.demo.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderRequest implements Serializable {
	/**
	 * 顧客id
	 * */
	private String customerid;

	/**
	 * 顧客名
	 * */
	private String customer;

	/**
	 * 受注日
	 * */
	private String orderdate;

	/**
	 * S番号
	 * */
	private String snumber;

	/**
	 * 件名
	 * */
	private String title;

	/**
	 * 数量
	 * */
	private String count;

	/**
	 * 納入指定日
	 * */
	private String specifieddate;

	/**
	 * 納入日
	 * */
	private String deliverydate;

	/**
	 * 請求日
	 * */
	private String billingdate;

	/**
	 * 見積金額
	 * */
	private int quoteprice;

	/**
	 * 受注金額
	 * */
	private int orderprice;

	/**
	 * ステータスid
	 * */
	private String statusid;

	/**
	 * ステータス名
	 * */
	private String status;

	/**
	 * 備考
	 * */
	private String note;
}
