package com.example.demo.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderUpdateRequest extends OrderRequest {
	/**
	 * 一覧画面のNo.のこと
	 * salesテーブルではidカラムを指す
	 * */
	@NotNull
	private int id;
	/**
	 * 削除フラグ
	 * 0 or 1 が入る
	 * 0 … 表示
	 * 1 … 非表示
	 * */
	@NotNull
	private String delete_flg = "0";
}
