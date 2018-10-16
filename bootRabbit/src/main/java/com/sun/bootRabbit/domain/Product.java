package com.sun.bootRabbit.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	
	public Product() {
		
	}
	
	public Product(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
