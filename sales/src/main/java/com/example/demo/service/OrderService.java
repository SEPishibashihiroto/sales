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
import com.example.demo.entity.Order;
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
	 *  SQLやDBに関するメソッドを扱う
	 *  DBやSQLを使用する際に不必要なものを削除するメソッドも含む
	 *
	 *  使用するRepository
	 *  ・OrderRepository     …  一覧表示に使用
	 *  ・CustomerRepository  …  顧客テーブルから値を取得する際に使用
	 *  ・StatusRepository    …  ステータステーブルから値を取得する際に使用
	 *  ・UpdateRepository    …  編集と削除を実行する際に使用
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
	//検索条件あるときに使用
	public Page<Order> getSeachUsers(String SeachCustomer, String SeachTitle, String SeachStatus, Pageable pageable) {
		return orderRepository.findSeachAll(SeachCustomer, SeachTitle, SeachStatus, pageable);
	}

	//検索条件がないときに使用
	public Page<Order> getSeachUsers(Pageable pageable) {
		return orderRepository.findSeachAll(pageable);
	}

	/**
	 * 顧客取得
	 */
	public List<Customer> getCustomer() {
		return customerRepository.findCustomerAll();
	}

	/**
	 * ステータス取得
	 */
	public List<Status> getStatus() {
		return statusRepository.findStatusAll();
	}

	/**
	 * 登録実行
	 */
	public void create(OrderRequest addOrderRequest) {
		Update order = new Update();
		order.setCustomerid(addOrderRequest.getCustomerid());
		order.setOrderdate(deletesura(addOrderRequest.getOrderdate()));
		order.setSnumber(addOrderRequest.getSnumber());
		order.setTitle(addOrderRequest.getTitle());
		order.setCount(addOrderRequest.getCount());
		order.setSpecifieddate(deletesura(addOrderRequest.getSpecifieddate()));
		order.setDeliverydate(deletesura(addOrderRequest.getDeliverydate()));
		order.setBillingdate(deletesura(addOrderRequest.getBillingdate()));
		order.setQuoteprice(addOrderRequest.getQuoteprice());
		order.setOrderprice(addOrderRequest.getOrderprice());
		order.setStatusid(addOrderRequest.getStatusid());
		order.setDelete_flg("0");
		updateRepository.save(order);
	}

	/**
	 * 一覧画面のNo.の番号検索してその番号の情報を取得
	 */
	public Update findUpdateById(int id) {
		return updateRepository.findById(id).get();
	}

	/**
	 * 編集を実行
	 */
	public void update(OrderUpdateRequest editOrderRequest) {
		Update order = findUpdateById(editOrderRequest.getId());
		order.setId(editOrderRequest.getId());
		order.setCustomerid(editOrderRequest.getCustomerid());
		order.setOrderdate(deletesura(editOrderRequest.getOrderdate()));
		order.setSnumber(editOrderRequest.getSnumber());
		order.setTitle(editOrderRequest.getTitle());
		order.setCount(editOrderRequest.getCount());
		order.setSpecifieddate(deletesura(editOrderRequest.getSpecifieddate()));
		order.setDeliverydate(deletesura(editOrderRequest.getDeliverydate()));
		order.setBillingdate(deletesura(editOrderRequest.getBillingdate()));
		order.setQuoteprice(editOrderRequest.getQuoteprice());
		order.setOrderprice(editOrderRequest.getOrderprice());
		order.setStatusid(editOrderRequest.getStatusid());
		order.setDelete_flg(editOrderRequest.getDelete_flg());
		System.out.println(order);
	}

	/**
	 * 削除を実行(論理削除)
	 */
	public void delete(OrderUpdateRequest deleteOrderRequest) {
		Update order = findUpdateById(deleteOrderRequest.getId());
		order.setOrderdate(deletesura(deleteOrderRequest.getOrderdate()));
		order.setSnumber(deleteOrderRequest.getSnumber());
		order.setSpecifieddate(deletesura(deleteOrderRequest.getSpecifieddate()));
		order.setDeliverydate(deletesura(deleteOrderRequest.getDeliverydate()));
		order.setBillingdate(deletesura(deleteOrderRequest.getBillingdate()));
		order.setBillingdate(deletesura(deleteOrderRequest.getBillingdate()));
		order.setQuoteprice(deleteOrderRequest.getQuoteprice());
		order.setOrderprice(deleteOrderRequest.getOrderprice());
		order.setDelete_flg(deleteOrderRequest.getDelete_flg());
		System.out.println(order);
	}

	/**
	 * データベース登録、アップデートにおいて不必要なものを削除する
	 * */
	//日付の'/'は不要なため消す
	private String deletesura(String s) {
		return (s.equals("")) ? s : s.replace("/", "");
	}

}
