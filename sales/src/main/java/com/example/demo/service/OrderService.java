package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderRequest;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order2;
import com.example.demo.entity.Status;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.StatusRepository;

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
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private StatusRepository statusRepository;

	/**
	 * ユーザー情報 全検索
	 * @return 検索結果
	 */
	public Page<Order2> getSeachUsers(String SeachCustomer, String SeachTitle, String SeachStatus, Pageable pageable) {
		return orderRepository.findSeachAll(SeachCustomer, SeachTitle, SeachStatus, pageable);
	}

	/**
	 * 顧客取得
	 */
	public List<Customer> getCustomer() {
		return customerRepository.findCustomerAll();
	}

	public List<Status> getStatus() {
		return statusRepository.findStatusAll();
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
