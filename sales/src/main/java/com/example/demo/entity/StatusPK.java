package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class StatusPK implements Serializable {
	@Column(name = "customerid")
	private int customerid;

	@Column(name = "statusid")
	private int statusid;

}
