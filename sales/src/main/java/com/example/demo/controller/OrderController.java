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
import com.example.demo.dto.SeachRequest;
import com.example.demo.dto.addOrderRequest;
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
	public String displayList(@PageableDefault(page = 0, size = 10) Pageable pageable,
			@ModelAttribute("SeachRequest") SeachRequest SeachRequest, Model model) {
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
		//		model.addAttribute("orders", userPage.getContent());
		model.addAttribute("SeachRequest", SeachRequest);

		return "sales/List";
	}

	/**
	 * 登録画面を表示
	*/
	@GetMapping(value = "/sales/Add")
	public String displayAdd(@ModelAttribute("addOrderRequest") OrderRequest addOrderRequest, Model model) {
		if (addOrderRequest.getSnumber() != null)
			addOrderRequest.setSnumber(deletesunmber(addOrderRequest.getSnumber()));

		model.addAttribute("customers", createListC());
		model.addAttribute("statuses", createListS());
		model.addAttribute("addOrderRequest",
				(addOrderRequest.getCustomerid() == null) ? addOrderRequest : setValue(addOrderRequest));
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
		addOrderRequest.setSnumber(writesunmber(addOrderRequest.getSnumber()));

		model.addAttribute("addOrderRequest", addOrderRequest);
		model.addAttribute("clist", createListC());
		model.addAttribute("slist", createListS());

		model.addAttribute("orderPrice", writekanma(addOrderRequest.getOrderprice()));
		model.addAttribute("quotePrice", writekanma(addOrderRequest.getQuoteprice()));
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
	public String displayEdit(@Validated @ModelAttribute("editOrderRequest") addOrderRequest editOrderRequest,
			@PathVariable int id, Model model) {
		if (editOrderRequest.getSnumber() != null)
			editOrderRequest.setSnumber(deletesunmber(editOrderRequest.getSnumber()));

		model.addAttribute("customers", createListC());
		model.addAttribute("statuses", createListS());
		model.addAttribute("editOrderRequest",
				(editOrderRequest.getCustomerid() == null) ? setValue(id) : setValue(editOrderRequest));
		return "sales/Edit";
	}

	/**
	 * 編集エラーチャック
	 * エラーなし　→　編集確認画面へ
	 * エラーあり　→　エラー文を持って編集画面へ
	*/
	@PostMapping(value = "/sales/editerrcheck")
	public String editerrcheck(@Validated @ModelAttribute("editOrderRequest") addOrderRequest editOrderRequest,
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
		editOrderRequest.setSnumber(writesunmber(editOrderRequest.getSnumber()));

		model.addAttribute("clist", createListC());
		model.addAttribute("slist", createListS());
		model.addAttribute("orderPrice", writekanma(editOrderRequest.getOrderprice()));
		model.addAttribute("quotePrice", writekanma(editOrderRequest.getQuoteprice()));
		model.addAttribute("editOrderRequest", editOrderRequest);
		return "sales/EditCheck";
	}

	/**
	 * 編集実行
	*/
	@PostMapping(value = "/sales/update")
	public String update(@Validated @ModelAttribute("editOrderRequest") addOrderRequest editOrderRequest,
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

		model.addAttribute("clist", createListC());
		model.addAttribute("slist", createListS());
		model.addAttribute("deleteUserRequest", setValue(id));

		return "sales/Delete";
	}

	/**
	 * 削除実行
	*/
	@PostMapping(value = "/sales/delete")
	public String delete(@Validated @ModelAttribute("deleteUserRequest") addOrderRequest deleteOrderRequest,
			BindingResult result,
			Model model) {

		deleteOrderRequest.setDelete_flg("1");

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
	private addOrderRequest setValue(int id) {
		Update order = orderService.findUpdateById(id);
		addOrderRequest orderUpdateRequest = new addOrderRequest();
		orderUpdateRequest.setId(order.getId());
		orderUpdateRequest.setCustomerid(order.getCustomerid());
		orderUpdateRequest.setOrderdate(writesura(order.getOrderdate()));
		orderUpdateRequest.setSnumber(order.getSnumber());
		orderUpdateRequest.setTitle(order.getTitle());
		orderUpdateRequest.setCount(order.getCount());
		orderUpdateRequest.setSpecifieddate(writesura(order.getSpecifieddate()));
		orderUpdateRequest.setDeliverydate(writesura(order.getDeliverydate()));
		orderUpdateRequest.setBillingdate(writesura(order.getBillingdate()));
		orderUpdateRequest.setQuoteprice(order.getQuoteprice());
		orderUpdateRequest.setOrderprice(order.getOrderprice());
		orderUpdateRequest.setStatusid(order.getStatusid());
		orderUpdateRequest.setDelete_flg(order.getDelete_flg());

		return orderUpdateRequest;
	}

	//編集確認画面から戻るボタンを使用して編集画面に遷移した際に入力情報をセット
	private addOrderRequest setValue(addOrderRequest OrderUpdateRequest) {
		addOrderRequest orderUpdateRequest = new addOrderRequest();
		orderUpdateRequest.setId(OrderUpdateRequest.getId());
		orderUpdateRequest.setCustomerid(OrderUpdateRequest.getCustomerid());
		orderUpdateRequest.setOrderdate(OrderUpdateRequest.getOrderdate());
		orderUpdateRequest.setSnumber(OrderUpdateRequest.getSnumber());
		orderUpdateRequest.setTitle(OrderUpdateRequest.getTitle());
		orderUpdateRequest.setCount(OrderUpdateRequest.getCount());
		orderUpdateRequest.setSpecifieddate(OrderUpdateRequest.getSpecifieddate());
		orderUpdateRequest.setDeliverydate(OrderUpdateRequest.getDeliverydate());
		orderUpdateRequest.setBillingdate(OrderUpdateRequest.getBillingdate());
		orderUpdateRequest.setQuoteprice(OrderUpdateRequest.getQuoteprice());
		orderUpdateRequest.setOrderprice(OrderUpdateRequest.getOrderprice());
		orderUpdateRequest.setStatusid(OrderUpdateRequest.getStatusid());
		orderUpdateRequest.setDelete_flg(OrderUpdateRequest.getDelete_flg());
		return orderUpdateRequest;
	}

	//登録確認画面から戻るボタンを使用して登録画面に遷移した際に入力情報をセット
	private OrderRequest setValue(OrderRequest addOrderRequest) {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setCustomerid(addOrderRequest.getCustomerid());
		orderRequest.setOrderdate(addOrderRequest.getOrderdate());
		orderRequest.setSnumber(addOrderRequest.getSnumber());
		orderRequest.setTitle(addOrderRequest.getTitle());
		orderRequest.setCount(addOrderRequest.getCount());
		orderRequest.setSpecifieddate(addOrderRequest.getSpecifieddate());
		orderRequest.setDeliverydate(addOrderRequest.getDeliverydate());
		orderRequest.setBillingdate(addOrderRequest.getBillingdate());
		orderRequest.setQuoteprice(addOrderRequest.getQuoteprice());
		orderRequest.setOrderprice(addOrderRequest.getOrderprice());
		orderRequest.setStatusid(addOrderRequest.getStatusid());
		return orderRequest;
	}

	/**
	 * データ表示において必要なものを挿入したり削除したりする
	 * */
	//編集画面の日付に’/’をつける
	private String writesura(String s) {
		return s.equals("") ? s : new StringBuilder(s).insert(4, "/").insert(7, "/").toString();
	}

	//確認画面のS番号に’S-’をつける
	private String writesunmber(String s) {
		return s.equals("") ? s : "S-" + s;
	}

	//登録・編集画面のS番号の’S-’を消す
	private String deletesunmber(String s) {
		return (s.lastIndexOf("S-") != -1) ? s.replace("S-", "") : s;
	}

	//確認画面の金額にカンマと'\'をつける
	private String writekanma(int n) {
		return n == 0 ? "" + n : "\\" + String.format("%,d", n);
	}

}
