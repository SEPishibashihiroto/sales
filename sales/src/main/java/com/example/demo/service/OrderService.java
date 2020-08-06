package com.example.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
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
	public Page<Order> getSeachUsers(String SeachName, Pageable pageable) {
		return orderRepository.findSeachAll(SeachName, pageable);
	}
}
