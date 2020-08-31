package com.example.demo.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class OrderRequest {
	@NotEmpty(message = "顧客は必須項目です")
	private String customerid;
	private String customer;
	private String orderdate;
	private String snumber;
	@NotEmpty(message = "件名は必須項目です")
	private String title;
	private String count;
	private String specifieddate;
	private String deliverydate;
	private String billingdate;
	private String quoteprice;
	private String orderprice;
	private String statusid;
	private String status;
	private String note;
}
