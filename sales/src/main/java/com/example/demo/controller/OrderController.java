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
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.SeachRequest;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order2;
import com.example.demo.entity.Status;
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

		String SeachCustomer = (SeachRequest.getSeachCustomer() == null) ? "" : SeachRequest.getSeachCustomer();
		String SeachTitle = (SeachRequest.getSeachTitle() == null) ? "" : SeachRequest.getSeachTitle();
		String SeachStatus = (SeachRequest.getSeachStatus() == null) ? "" : SeachRequest.getSeachStatus();

		Page<Order2> userPage = orderService.getSeachUsers(SeachCustomer, SeachTitle, SeachStatus, pageable);

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
		List<Customer> customerList = orderService.getCustomer();
		List<Status> statusList = orderService.getStatus();
		model.addAttribute("customers", customerList);
		model.addAttribute("statuses", statusList);
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
			model.addAttribute("addOrderRequest", addOrderRequest);
			return "sales/Add";
		}
		List<Customer> customerList = orderService.getCustomer();
		List<Status> statusList = orderService.getStatus();
		model.addAttribute("addOrderRequest", addOrderRequest);
		model.addAttribute("clist", customerList);
		model.addAttribute("slist", statusList);
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

	@GetMapping(value = "/sales/{id}/Edit")
	public String displayEdit(@PathVariable int id, Model model) {
		Order order = orderService.findById(id);
		OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();

		model.addAttribute("editOrderRequest", orderUpdateRequest);
		return "Address/Edit";
	}
	*/
	/**
	 * 編集エラーチャック
	 * エラーなし　→　編集確認画面へ
	 * エラーあり　→　エラー文を持って編集画面へ

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
			model.addAttribute("editOrderRequest", editOrderRequest);
			return "sales/Edit";
		}
		model.addAttribute("editOrderRequest", editOrderRequest);
		return "sales/EditCheck";
	}
	*/
	/**
	 * 編集実行

	@PostMapping(value = "/Address/update")
	public String update(@Validated @ModelAttribute("editOrderRequest") OrderUpdateRequest editOrderRequest,
			BindingResult result,
			Model model) {
		// ユーザー情報の登録
		model.addAttribute("editUserRequest", editOrderRequest);

		orderService.update(editOrderRequest);
		return "redirect:/Address/List";
	}
	*/
	/**
	 * 削除画面を表示

	@GetMapping(value = "/sales/{id}/Delete")
	public String displayDelete(@PathVariable int id, Model model) {
		Order order = orderService.findById(id);
		OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
		orderUpdateRequest.setId(order.getId());
		orderUpdateRequest.setDelete_flg(order.getDelete_flg());

		model.addAttribute("deleteUserRequest", orderUpdateRequest);
		return "sales/Delete";
	}
	*/
	/**
	 * 削除実行

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
	*/
}
