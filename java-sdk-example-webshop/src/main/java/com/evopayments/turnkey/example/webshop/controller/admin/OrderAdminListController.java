package com.evopayments.turnkey.example.webshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.evopayments.turnkey.example.webshop.repository.OrderEntityRepository;

@Controller
public class OrderAdminListController {

	@Autowired
	private OrderEntityRepository orderEntityRepository;

	@GetMapping("/admin/orders")
	public String orderList(Model model) {
		model.addAttribute("orders", orderEntityRepository.findTop50ByOrderByCreatedOnDesc());
		return "/admin/orders";
	}

}
