package com.evopayments.example.webshop.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evopayments.example.webshop.data.OrderActionResponseDto;
import com.evopayments.example.webshop.data.OrderEntity;
import com.evopayments.example.webshop.repository.OrderEntityRepository;
import com.evopayments.example.webshop.util.TurnkeyJavaSdkHelper3;

@RestController
public class OrderAdminActionController {

	@Autowired
	private OrderEntityRepository orderEntityRepository;

	@GetMapping("/admin/orders/{orderId}")
	public OrderActionResponseDto getOrderWithStatusCheck(@PathVariable String orderId) {

		OrderEntity orderEntity = orderEntityRepository.findById(orderId).get();

		JSONObject jsonObject = TurnkeyJavaSdkHelper3.statusCheck(orderEntity.getId());

		OrderActionResponseDto orderActionResponseDto = new OrderActionResponseDto();
		orderActionResponseDto.setResponseFromTurnkeyApi(jsonObject.toString(2));
		return orderActionResponseDto;
	}

	@PostMapping("/admin/orders/{orderId}/captures")
	public OrderActionResponseDto executeCapture(@PathVariable String orderId) {

		OrderEntity orderEntity = orderEntityRepository.findById(orderId).get();

		JSONObject jsonObject = TurnkeyJavaSdkHelper3.executeCapture(orderEntity);

		OrderActionResponseDto orderActionResponseDto = new OrderActionResponseDto();
		orderActionResponseDto.setResponseFromTurnkeyApi(jsonObject.toString(2));
		return orderActionResponseDto;

	}

	@PostMapping("/admin/orders/{orderId}/voids")
	public OrderActionResponseDto executeVoid(@PathVariable String orderId) {

		OrderEntity orderEntity = orderEntityRepository.findById(orderId).get();

		JSONObject jsonObject = TurnkeyJavaSdkHelper3.executeVoid(orderEntity.getId());

		OrderActionResponseDto orderActionResponseDto = new OrderActionResponseDto();
		orderActionResponseDto.setResponseFromTurnkeyApi(jsonObject.toString(2));
		return orderActionResponseDto;

	}

	@PostMapping("/admin/orders/{orderId}/refunds")
	public OrderActionResponseDto executeRefund(@PathVariable String orderId) {

		OrderEntity orderEntity = orderEntityRepository.findById(orderId).get();

		JSONObject jsonObject = TurnkeyJavaSdkHelper3.executeRefund(orderEntity);

		OrderActionResponseDto orderActionResponseDto = new OrderActionResponseDto();
		orderActionResponseDto.setResponseFromTurnkeyApi(jsonObject.toString(2));
		return orderActionResponseDto;

	}

}
