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
import com.example.demo.entity.Order;
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

		String SeachName = (SeachRequest.getSeachOrder() == null) ? "" : SeachRequest.getSeachOrder();

		Page<Order> userPage = orderService.getSeachUsers(SeachName, pageable);

		model.addAttribute("page", userPage);
		model.addAttribute("users", userPage.getContent());
		model.addAttribute("SeachName", SeachName);
		model.addAttribute("SeachRequest", SeachRequest);

		return "sales/List";
	}
}
