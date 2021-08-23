package com.evopayments.example.webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.evopayments.example.webshop.repository.OrderEntityRepository;

@Controller
public class OrderListController {

	@Autowired
	private OrderEntityRepository orderEntityRepository;

	@GetMapping("/orders")
	public String orderList(Model model) {
		model.addAttribute("orders", orderEntityRepository.findTop50ByOrderByCreatedOnDesc());
		return "orders";
	}

}
