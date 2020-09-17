package com.example.demo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	@Pattern(regexp = "\\d{4}$", message = "S番号は数値形式で入力してください")
	private String snumber;

	/**
	 * 件名
	 * */
	@Size(max = 20, message = "「件名」は20字以内で入力してください")
	@NotBlank(message = "「件名」は必須項目です")
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
