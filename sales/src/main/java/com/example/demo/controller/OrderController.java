package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderUpdateRequest;
import com.example.demo.dto.SeachRequest;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.Status;
import com.example.demo.entity.Update;
import com.example.demo.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;

	/**
	 * 案件一覧画面を表示
	 */
	@GetMapping(value = "/sales/List")
	public String displayList(@PageableDefault(page = 0, size = 10) Pageable pageable, Model model,
			@ModelAttribute("SeachRequest") SeachRequest SeachRequest) {
		String SeachCustomer = (SeachRequest.getSeachCustomer() == null
				|| SeachRequest.getSeachCustomer().equals("bran")) ? "" : SeachRequest.getSeachCustomer();
		String SeachTitle = (SeachRequest.getSeachTitle() == null) ? "" : SeachRequest.getSeachTitle();
		String SeachStatus = (SeachRequest.getSeachStatus() == null) ? "" : SeachRequest.getSeachStatus();

		Page<Order> userPage = (SeachCustomer.equals("") && SeachTitle.equals("") && SeachStatus.equals(""))
				? orderService.getSeachUsers(pageable)
				: orderService.getSeachUsers(SeachCustomer, SeachTitle, SeachStatus, pageable);

		model.addAttribute("customers", createListC());
		model.addAttribute("statuses", createListS());
		model.addAttribute("page", userPage);
		model.addAttribute("orders", userPage.getContent());
		model.addAttribute("SeachCustomer", SeachCustomer);
		model.addAttribute("SeachTitle", SeachTitle);
		model.addAttribute("SeachStatus", SeachStatus);
		model.addAttribute("SeachRequest", SeachRequest);

		return "sales/List";
	}

	/**
	 * 登録画面を表示
	*/
	@GetMapping(value = "/sales/Add")
	public String displayAdd(@ModelAttribute("addOrderRequest") OrderRequest addOrderRequest, Model model) {
		model.addAttribute("customers", createListC());
		model.addAttribute("statuses", createListS());
		model.addAttribute("addOrderRequest", addOrderRequest);
		return "sales/Add";
	}

	/**
	 * 登録エラーチャック
	 * エラーなし　→　登録確認画面へ
	 * エラーあり　→　エラー文を持って登録画面へ
	*/
	@PostMapping(value = "/sales/adderrcheck")
	public String adderrcheck(@Validated @ModelAttribute("addOrderRequest") OrderRequest addOrderRequest,
			BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			model.addAttribute("clist", createListC());
			model.addAttribute("slist", createListS());
			model.addAttribute("addOrderRequest", addOrderRequest);
			return "sales/Add";
		}

		model.addAttribute("addOrderRequest", addOrderRequest);
		model.addAttribute("clist", createListC());
		model.addAttribute("slist", createListS());
		return "sales/AddCheck";
	}

	/**
	 * 登録実行
	*/
	@PostMapping(value = "/sales/create")
	public String create(@Validated @ModelAttribute("addOrderRequest") OrderRequest addOrderRequest,
			BindingResult result,
			Model model) {
		// ユーザー情報の登録
		model.addAttribute("addOrderRequest", addOrderRequest);
		orderService.create(addOrderRequest);
		return "redirect:/sales/List";
	}

	/**
	 * 編集画面を表示
	*/
	@GetMapping(value = "/sales/{id}/Edit")
	public String displayEdit(@PathVariable int id, Model model) {
		OrderUpdateRequest orderUpdateRequest = setValue(id);

		model.addAttribute("customers", createListC());
		model.addAttribute("statuses", createListS());
		model.addAttribute("editOrderRequest", orderUpdateRequest);
		return "sales/Edit";
	}

	/**
	 * 編集エラーチャック
	 * エラーなし　→　編集確認画面へ
	 * エラーあり　→　エラー文を持って編集画面へ
	*/
	@PostMapping(value = "/sales/editerrcheck")
	public String editerrcheck(@Validated @ModelAttribute("editOrderRequest") OrderUpdateRequest editOrderRequest,
			BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			model.addAttribute("clist", createListC());
			model.addAttribute("slist", createListS());
			model.addAttribute("editOrderRequest", editOrderRequest);
			return "sales/Edit";
		}

		model.addAttribute("clist", createListC());
		model.addAttribute("slist", createListS());
		model.addAttribute("editOrderRequest", editOrderRequest);
		return "sales/EditCheck";
	}

	/**
	 * 編集実行
	*/
	@PostMapping(value = "/sales/update")
	public String update(@Validated @ModelAttribute("editOrderRequest") OrderUpdateRequest editOrderRequest,
			BindingResult result,
			Model model) {
		// ユーザー情報の登録
		model.addAttribute("editUserRequest", editOrderRequest);

		orderService.update(editOrderRequest);
		return "redirect:/sales/List";
	}

	/**
	 * 削除画面を表示
	*/
	@GetMapping(value = "/sales/{id}/Delete")
	public String displayDelete(@PathVariable int id, Model model) {
		OrderUpdateRequest orderUpdateRequest = setValue(id);

		model.addAttribute("clist", createListC());
		model.addAttribute("slist", createListS());
		model.addAttribute("deleteUserRequest", orderUpdateRequest);
		return "sales/Delete";
	}

	/**
	 * 削除実行
	*/
	@PostMapping(value = "/sales/delete")
	public String delete(@Validated @ModelAttribute("deleteUserRequest") OrderUpdateRequest deleteOrderRequest,
			BindingResult result,
			Model model) {
		String deleteflg = "1";

		deleteOrderRequest.setDelete_flg(deleteflg);

		model.addAttribute("deleteOrderRequest", deleteOrderRequest);

		orderService.delete(deleteOrderRequest);
		return "redirect:/sales/List";
	}

	//顧客のプルダウン作成に必要なものを取得
	private List<Customer> createListC() {
		return orderService.getCustomer();
	}

	//ステータスのプルダウン作成に必要なものを取得
	private List<Status> createListS() {
		return orderService.getStatus();
	}

	//編集や削除で取得した情報を実行で使用する型に情報をセット
	private OrderUpdateRequest setValue(int id) {
		Update order = orderService.findUpdateById(id);
		OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
		orderUpdateRequest.setId(order.getId());
		orderUpdateRequest.setCustomerid(order.getCustomerid());
		orderUpdateRequest.setOrderdate(order.getOrderdate());
		orderUpdateRequest.setSnumber(order.getSnumber());
		orderUpdateRequest.setTitle(order.getTitle());
		orderUpdateRequest.setCount(order.getCount());
		orderUpdateRequest.setSpecifieddate(order.getSpecifieddate());
		orderUpdateRequest.setDeliverydate(order.getDeliverydate());
		orderUpdateRequest.setBillingdate(order.getBillingdate());
		orderUpdateRequest.setQuoteprice(order.getQuoteprice());
		orderUpdateRequest.setOrderprice(order.getOrderprice());
		orderUpdateRequest.setStatusid(order.getStatusid());
		orderUpdateRequest.setDelete_flg(order.getDelete_flg());

		return orderUpdateRequest;
	}

}
