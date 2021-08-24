package com.evopayments.example.webshop.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.encoder.Encode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.evopayments.turnkey.util.HttpParamUtil;

@Controller
public class TransactionResultController {

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value =  "/transactionresult")
	public String transactionResult(Model model, HttpServletRequest req, HttpServletResponse resp) {
				
		Map<String, String> parameterMap = HttpParamUtil.extractParams(req);
		
		model.addAttribute("status", Encode.forHtml(parameterMap.get("status")));
		model.addAttribute("orderId", "?"); // TODO: ...
		
		return "/transactionresult";
	}

}
