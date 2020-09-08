package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * クラス Status で使用
 * 複合キーであることを宣言するためのクラス
 * */
@Data
@Embeddable
public class StatusPK implements Serializable {
	/**
	 * 顧客id
	 * */
	@Column(name = "customerid")
	private int customerid;

	/**
	 * ステータスid
	 * */
	@Column(name = "statusid")
	private int statusid;

}
