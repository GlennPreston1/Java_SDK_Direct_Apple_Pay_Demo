package com.evopayments.example.webshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;

public class TransactionResultCallbackController {

	@GetMapping("/transactionresultcallback")
	public void transactionResultVallback(HttpServletRequest req, HttpServletResponse resp) {
		
	}

}
