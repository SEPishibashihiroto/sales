package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "sales")
public class Update {
	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * 顧客
	 */
	private String customerid;

	/**
	 * 受注日
	 */
	private String orderdate;

	/**
	* S番号
	*/
	private String snumber;

	/**
	* 件名
	*/
	private String title;

	/**
	* 数量
	*/
	private String count;

	/**
	* 納入指定日
	*/
	private String specifieddate;

	/**
	 *納入日
	 */
	private String deliverydate;

	/**
	 *請求日
	 */
	private String billingdate;

	/**
	 *見積金額
	 */
	private String quoteprice;

	/**
	 *受注金額
	 */
	private String orderprice;

	/**
	 *ステータス
	 */
	private String statusid;

	/**
	 *デリートフラグ
	 */
	private String delete_flg;
}
