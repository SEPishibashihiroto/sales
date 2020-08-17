package com.example.demo.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class OrderRequest {
	@NotEmpty(message = "顧客は必須項目です")
	String customerid;
	String orderdate;
	String snumber;
	@NotEmpty(message = "件名は必須項目です")
	String title;
	String count;
	String specifieddate;
	String deliverydate;
	String billingdate;
	String quoteprice;
	String orderprice;
	String statusid;
}
