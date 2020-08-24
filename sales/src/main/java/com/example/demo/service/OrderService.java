package com.example.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderRequest;
import com.example.demo.entity.Order2;
import com.example.demo.repository.OrderRepository;

/**
 * ユーザー情報 Service
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class OrderService {
	/**
	 * ユーザー情報 Repository
	 */
	@Autowired
	private OrderRepository orderRepository;

	/**
	 * ユーザー情報 全検索
	 * @return 検索結果
	 */
	public Page<Order2> getSeachUsers(String SeachCustomer, String SeachTitle, String SeachStatus, Pageable pageable) {
		return orderRepository.findSeachAll(SeachCustomer, SeachTitle, SeachStatus, pageable);
	}

	public void create(OrderRequest addOrderRequest) {

	}

	/**
	public Order findById(int id) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void update(OrderUpdateRequest editOrderRequest) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void delete(OrderUpdateRequest deleteOrderRequest) {
		// TODO 自動生成されたメソッド・スタブ

	}
	*/
}
