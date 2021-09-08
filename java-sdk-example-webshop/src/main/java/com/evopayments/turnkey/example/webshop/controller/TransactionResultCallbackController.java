package com.evopayments.turnkey.example.webshop.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.evopayments.turnkey.example.webshop.data.OrderEntity;
import com.evopayments.turnkey.example.webshop.repository.OrderEntityRepository;
import com.evopayments.turnkey.example.webshop.util.TurnkeyJavaSdkHelper3;
import com.evopayments.turnkey.util.HttpParamUtil;

/**
 * Receiver for server to server notifications (example)
 */
@RestController
public class TransactionResultCallbackController {
	
	@Autowired
	private OrderEntityRepository orderEntityRepository;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/transactionresultcallback")
	public void transactionResultCallback(HttpServletRequest req, HttpServletResponse resp) {
		
		Map<String, String> parameterMap = HttpParamUtil.extractParams(req);
		String orderId = parameterMap.get("merchantTxId");
		
		OrderEntity orderEntity = orderEntityRepository.findById(orderId).get();
		
		JSONObject joStatusCheck = TurnkeyJavaSdkHelper3.statusCheck(orderId); // double check the status
		String status = joStatusCheck.getString("status");
		
		orderEntity.setStatus(status);
		orderEntityRepository.save(orderEntity);
		
	}

}
