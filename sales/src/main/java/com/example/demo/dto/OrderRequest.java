package com.example.demo.dto;

import lombok.Data;

@Data
public class OrderRequest {
	private String customerid;
	private String customer;
	private String orderdate;
	private String snumber;
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
