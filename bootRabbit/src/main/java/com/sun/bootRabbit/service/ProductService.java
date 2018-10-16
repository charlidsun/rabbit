package com.sun.bootRabbit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sun.bootRabbit.domain.Product;

@Service
public class ProductService {

	public List<Product> listProduct() {
		List<Product> list = new ArrayList<>();
		list.add(new Product(1, "1111"));
		list.add(new Product(2, "2222"));
		list.add(new Product(3, "3333"));
		return list;
	}
}
