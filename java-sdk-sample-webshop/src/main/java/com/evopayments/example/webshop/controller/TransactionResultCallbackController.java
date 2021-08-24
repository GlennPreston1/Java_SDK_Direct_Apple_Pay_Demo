package com.evopayments.example.webshop.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evopayments.turnkey.util.HttpParamUtil;

@RestController
public class TransactionResultCallbackController {

	@GetMapping("/transactionresultcallback")
	public void transactionResultVallback(HttpServletRequest req, HttpServletResponse resp) {
		
		Map<String, String> parameterMap = HttpParamUtil.extractParams(req);
		
		// model.addAttribute("status", Encode.forHtml(parameterMap.get("status")));
		// model.addAttribute("orderId", "?"); // TODO: ..
		
	}

}
