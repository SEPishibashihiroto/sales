package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

@Entity
@Data
@IdClass(StatusPK.class)
public class Status {
	@Id
	private int customerid;
	@Id
	private int statusid;

	private String status;
}
