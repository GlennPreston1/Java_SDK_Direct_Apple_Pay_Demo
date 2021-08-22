package com.evopayments.example.webshop.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.evopayments.example.webshop.data.OrderEntity;
import com.evopayments.example.webshop.repository.OrderEntityRepository;

@Controller
public class OrderListController {

	@Autowired
	private OrderEntityRepository orderEntityRepository;

	@GetMapping("/orders")
	public String greeting(Model model) {

		OrderEntity order = new OrderEntity();
		order.setOrderDescription("test");
		order.setCurrency("EUR");
		order.setAmount(15);
		order.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		orderEntityRepository.save(order);

		//

		model.addAttribute("orders", orderEntityRepository.findTop50ByOrderByCreatedOnDesc());
		return "orders";
	}

}
