package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "sales")
public class Order {
	/**
	 * ID
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	/**
	 * 顧客
	 * 顧客名が格納
	 */
	@Column(name = "customer")
	private String customer;
	/**
	 * 受注日
	 */
	@Column(name = "orderdate")
	private String orderdate;
	/**
	* S番号
	*/
	@Column(name = "snumber")
	private String snumber;
	/**
	* 件名
	*/
	@Column(name = "title")
	private String title;
	/**
	* 数量
	*/
	@Column(name = "count")
	private String count;
	/**
	* 納入指定日
	*/
	@Column(name = "specifieddate")
	private String specifieddate;
	/**
	 *納入日
	 */
	@Column(name = "deliverydate")
	private String deliverydate;
	/**
	 *請求日
	 */
	@Column(name = "billingdate")
	private String billingdate;
	/**
	 *見積金額
	 */
	@Column(name = "quoteprice")
	private String quoteprice;
	/**
	 *受注金額
	 */
	@Column(name = "orderprice")
	private String orderprice;
	/**
	 *ステータス
	 */
	@Column(name = "status")
	private String status;

}
