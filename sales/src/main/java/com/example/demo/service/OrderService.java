package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderUpdateRequest;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order2;
import com.example.demo.entity.Status;
import com.example.demo.entity.Update;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.UpdateRepository;

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
	@Autowired
	private UpdateRepository updateRepository;

	/**
	 * ユーザー情報 全検索
	 * @return 検索結果
	 */
	public Page<Order2> getSeachUsers(String SeachCustomer, String SeachTitle, String SeachStatus, Pageable pageable) {
		return orderRepository.findSeachAll(SeachCustomer, SeachTitle, SeachStatus, pageable);
	}

	public Page<Order2> getSeachUsers(Pageable pageable) {
		return orderRepository.findSeachAll(pageable);
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
		Update order = new Update();
		order.setCustomerid(addOrderRequest.getCustomerid());
		order.setOrderdate(addOrderRequest.getOrderdate());
		order.setSnumber(addOrderRequest.getSnumber());
		order.setTitle(addOrderRequest.getTitle());
		order.setCount(addOrderRequest.getCount());
		order.setSpecifieddate(addOrderRequest.getSpecifieddate());
		order.setDeliverydate(addOrderRequest.getDeliverydate());
		order.setBillingdate(addOrderRequest.getBillingdate());
		order.setQuoteprice(addOrderRequest.getQuoteprice());
		order.setOrderprice(addOrderRequest.getOrderprice());
		order.setStatusid(addOrderRequest.getStatusid());
		order.setDelete_flg("0");
		updateRepository.save(order);
	}

	public Order2 findById(int id) {
		return orderRepository.findById(id).get();
	}

	public Update findUpdateById(int id) {
		return updateRepository.findById(id).get();
	}

	public void update(OrderUpdateRequest editOrderRequest) {
		Update order = findUpdateById(editOrderRequest.getId());
		order.setId(editOrderRequest.getId());
		order.setCustomerid(editOrderRequest.getCustomerid());
		order.setOrderdate(editOrderRequest.getOrderdate());
		order.setSnumber(editOrderRequest.getSnumber());
		order.setTitle(editOrderRequest.getTitle());
		order.setCount(editOrderRequest.getCount());
		order.setSpecifieddate(editOrderRequest.getSpecifieddate());
		order.setDeliverydate(editOrderRequest.getDeliverydate());
		order.setBillingdate(editOrderRequest.getBillingdate());
		order.setQuoteprice(editOrderRequest.getQuoteprice());
		order.setOrderprice(editOrderRequest.getOrderprice());
		order.setStatusid(editOrderRequest.getStatusid());
		order.setDelete_flg(editOrderRequest.getDelete_flg());
	}

	public void delete(OrderUpdateRequest deleteOrderRequest) {
		Update order = findUpdateById(deleteOrderRequest.getId());
		order.setDelete_flg(deleteOrderRequest.getDelete_flg());
	}

}
