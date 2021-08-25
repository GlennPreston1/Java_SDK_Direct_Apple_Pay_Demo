package com.evopayments.turnkey.example.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.evopayments.turnkey.apiclient.AbstractApiCall;
import com.evopayments.turnkey.config.TestConfig;

@Controller
public class BasicIframeShopPageController {

	@GetMapping("/basic-iframe/shop")
	public String buildPage(Model model) {

		TestConfig testConfig = TestConfig.getInstance();

		model.addAttribute("cashierJsLibUrl", testConfig.getProperty(AbstractApiCall.CASHIER_JS_LIB_URL_PROP_KEY));
		model.addAttribute("cashierBaseUrl", testConfig.getProperty(AbstractApiCall.CASHIER_URL_PROP_KEY));
		model.addAttribute("merchantId", testConfig.getProperty(AbstractApiCall.MERCHANT_ID_PROP_KEY));

		return "/basic-iframe/shop";

	}

}
