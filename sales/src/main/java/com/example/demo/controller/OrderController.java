package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderUpdateRequest;
import com.example.demo.dto.SeachRequest;
import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;
import com.example.demo.service.VariousService;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	VariousService variousService;

	/**
	 * 案件一覧画面を表示
	 */
	@GetMapping(value = "/sales/List")
	public String displayList(@PageableDefault(page = 0, size = 10) Pageable pageable,
			@ModelAttribute("SeachRequest") SeachRequest SeachRequest, Model model) {
		String SeachCustomer = (SeachRequest.getSeachCustomer() == null
				|| SeachRequest.getSeachCustomer().equals("bran")) ? "" : SeachRequest.getSeachCustomer();
		String SeachTitle = (SeachRequest.getSeachTitle() == null) ? "" : SeachRequest.getSeachTitle();
		String SeachStatus = (SeachRequest.getSeachStatus() == null) ? "" : SeachRequest.getSeachStatus();
		Page<Order> userPage = (SeachCustomer.equals("") && SeachTitle.equals("") && SeachStatus.equals(""))
				? orderService.getSeachUsers(pageable)
				: orderService.getSeachUsers(SeachCustomer, SeachTitle, SeachStatus, pageable);
		variousService.addListModel(model);
		model.addAttribute("page", userPage);
		model.addAttribute("orders", userPage.getContent());
		model.addAttribute("SeachRequest", SeachRequest);
		return "sales/List";
	}
	/**
	 * 登録画面を表示
	*/
	@GetMapping(value = "/sales/Add")
	public String displayAdd(@ModelAttribute("addOrderRequest") OrderRequest addOrderRequest, Model model) {
		variousService.addListModel(model);
		model.addAttribute("addOrderRequest",
				(addOrderRequest.getCustomerid() == null) ? addOrderRequest : variousService.setValue(addOrderRequest));
		return "sales/Add";
	}
	/**
	 * 登録エラーチャック
	 * エラーなし　→　登録確認画面へ
	 * エラーあり　→　エラー文を持って登録画面へ
	*/
	@PostMapping(value = "/sales/adderrcheck")
	public String adderrcheck(@Validated @ModelAttribute("addOrderRequest") OrderRequest addOrderRequest,
			BindingResult result, Model model) {
		variousService.addListModel(model);
		List<String> errorList = variousService.createErrorList(addOrderRequest);
		if (errorList.size() != 0) {
			variousService.setIdes(addOrderRequest);
			model.addAttribute("validationError", errorList);
			model.addAttribute("addOrderRequest", addOrderRequest);
			return "sales/Add";
		}
		variousService.addIndividualModel(addOrderRequest, model);
		model.addAttribute("addOrderRequest", addOrderRequest);
		return "sales/AddCheck";
	}
	/**
	 * 登録実行
	*/
	@PostMapping(value = "/sales/create")
	public String create(@Validated @ModelAttribute("addOrderRequest") OrderRequest addOrderRequest,
			BindingResult result, Model model) {
		// ユーザー情報の登録
		model.addAttribute("addOrderRequest", addOrderRequest);
		orderService.create(addOrderRequest);
		return "redirect:/sales/List";
	}
	/**
	 * 編集画面を表示
	*/
	@GetMapping(value = "/sales/{id}/Edit")
	public String displayEdit(@Validated @ModelAttribute("editOrderRequest") OrderUpdateRequest editOrderRequest,
			@PathVariable int id, Model model) {
		variousService.addListModel(model);
		model.addAttribute("editOrderRequest",
				(editOrderRequest.getCustomerid() == null) ? variousService.setValue(id)
						: variousService.setValue(editOrderRequest));
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
		variousService.addListModel(model);
		List<String> errorList = variousService.createErrorList(editOrderRequest);
		if (errorList.size() != 0) {
			variousService.setIdes(editOrderRequest);
			model.addAttribute("validationError", errorList);
			model.addAttribute("editOrderRequest", editOrderRequest);
			return "sales/Edit";
		}
		variousService.addIndividualModel(editOrderRequest, model);
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
		variousService.addListModel(model);
		model.addAttribute("deleteOrderRequest", variousService.setValue(id));
		return "sales/Delete";
	}
	/**
	 * 削除実行
	*/
	@PostMapping(value = "/sales/delete")
	public String delete(@Validated @ModelAttribute("deleteOrderRequest") OrderUpdateRequest deleteOrderRequest,
			BindingResult result,
			Model model) {
		deleteOrderRequest.setDelete_flg("1");
		model.addAttribute("deleteOrderRequest", deleteOrderRequest);
		orderService.delete(deleteOrderRequest);
		return "redirect:/sales/List";
	}

}
