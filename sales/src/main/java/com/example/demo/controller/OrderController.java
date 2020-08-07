package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.dto.SeachRequest;
import com.example.demo.entity.Order2;
import com.example.demo.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;

	/**
	 * 案件一覧画面を表示
	 * @param model Model
	 * @return 案件一覧画面
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
}
