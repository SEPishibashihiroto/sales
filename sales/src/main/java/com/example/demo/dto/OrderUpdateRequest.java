package com.example.demo.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderUpdateRequest extends OrderRequest {
	@NotNull
	private int id;
	@NotNull
	private String delete_flg = "0";
}
