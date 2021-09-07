package com.evopayments.turnkey.example.webshop.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.evopayments.turnkey.example.webshop.data.OrderEntity;
import com.evopayments.turnkey.example.webshop.data.OrderSubmitRequestDto;
import com.evopayments.turnkey.example.webshop.data.OrderSubmitResponseDto;
import com.evopayments.turnkey.example.webshop.repository.OrderEntityRepository;
import com.evopayments.turnkey.example.webshop.util.TurnkeyJavaSdkHelper1;
import com.evopayments.turnkey.example.webshop.util.TurnkeyJavaSdkHelper2;

@RestController
public class OrderSubmitController {

	@Autowired
	private OrderEntityRepository orderEntityRepository;

	@PostMapping("/orders")
	public OrderSubmitResponseDto submitOrder(@RequestBody OrderSubmitRequestDto orderSubmitRequestDto) {

		if (!StringUtils.hasLength(orderSubmitRequestDto.getProduct())) {
			throw new IllegalArgumentException("Missing product!");
		}
		
		if (!StringUtils.hasLength(orderSubmitRequestDto.getMode())) {
			throw new IllegalArgumentException("Missing mode!");
		}
		
		OrderEntity orderEntity = saveOrder(orderSubmitRequestDto);
		
		OrderSubmitResponseDto orderSubmitResponseDto = startOrExecutePayment(orderSubmitRequestDto, orderEntity);
				
		return orderSubmitResponseDto;
	}

	private OrderEntity saveOrder(OrderSubmitRequestDto orderSubmitRequestDto) {
		
		OrderEntity orderEntity = new OrderEntity();

		switch (orderSubmitRequestDto.getProduct()) {
		case "Toy cars":
			orderEntity.setAmount(new BigDecimal(18));
			break;
		case "Table tennis set":
			orderEntity.setAmount(new BigDecimal(20));
			break;
		case "Teddy bear":
			orderEntity.setAmount(new BigDecimal(30));
			break;
		case "Football":
			orderEntity.setAmount(new BigDecimal(24));
			break;
		default:
			throw new IllegalArgumentException("Missing product!");
		}

		orderEntity.setId("sdkt" + Long.toString(System.currentTimeMillis()/1000L));
		orderEntity.setProduct(orderSubmitRequestDto.getProduct());
		orderEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		orderEntity.setStatus("started");
		
		orderEntity = orderEntityRepository.save(orderEntity);
		
		return orderEntity;
	}
	
	private static OrderSubmitResponseDto startOrExecutePayment(OrderSubmitRequestDto orderSubmitRequestDto, OrderEntity orderEntity) {
		
		OrderSubmitResponseDto orderSubmitResponseDto = new OrderSubmitResponseDto();
		
		switch (orderSubmitRequestDto.getMode()) {
		case "iframe":
			orderSubmitResponseDto.setTokenToInitJsLib(TurnkeyJavaSdkHelper1.startIframeModeCashierPayment(orderEntity));
			break;
		case "standalone":
			orderSubmitResponseDto.setUrl(TurnkeyJavaSdkHelper1.startStandaloneModeCashierPayment(orderEntity));
			break;
		case "hpp":
			orderSubmitResponseDto.setUrl(TurnkeyJavaSdkHelper1.startHppModeCashierPayment(orderEntity));
			break;
		case "advanced":
			orderSubmitResponseDto.setUrl(TurnkeyJavaSdkHelper2.executeAdvancedPayment(orderSubmitRequestDto, orderEntity));
			break;
		case "advanced-applepay":
			orderSubmitResponseDto.setUrl(TurnkeyJavaSdkHelper2.executeAdvancedPayment(orderSubmitRequestDto, orderEntity));
			break;
		default:
			throw new IllegalArgumentException("Incorrect mode!");
		}
		
		orderSubmitResponseDto.setOrderId(orderEntity.getId());
		orderSubmitResponseDto.setMode(orderSubmitRequestDto.getMode());
		
		return orderSubmitResponseDto;
	}

}
