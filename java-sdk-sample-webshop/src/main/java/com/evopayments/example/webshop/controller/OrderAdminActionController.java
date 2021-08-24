package com.evopayments.example.webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evopayments.example.webshop.data.OrderActionResponseDto;
import com.evopayments.example.webshop.repository.OrderEntityRepository;

@RestController
public class OrderAdminActionController {

	@Autowired
	private OrderEntityRepository orderEntityRepository;

	@GetMapping("/admin/orders/{orderId}")
	public OrderActionResponseDto getOrderWithStatusCheck(@PathVariable String orderId) {

		orderEntityRepository.findById(orderId);

		OrderActionResponseDto orderActionResponseDto = new OrderActionResponseDto();
		
		
		
		return orderActionResponseDto;
	}
	
	@PutMapping("/admin/orders/{orderId}")
	public String orderList(Model model) {
		model.addAttribute("orders", orderEntityRepository.findTop50ByOrderByCreatedOnDesc());
		return "/admin/orders";
	}

}
