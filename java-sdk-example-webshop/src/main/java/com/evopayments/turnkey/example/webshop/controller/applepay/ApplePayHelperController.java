package com.evopayments.turnkey.example.webshop.controller.applepay;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evopayments.turnkey.example.webshop.data.applepay.ApplePayCreateJsSessionResponseDto;
import com.evopayments.turnkey.util.DirectApiApplePayHelper;

/**
 * Relevant for the "Advanced/DirectAPI (Apple Pay) example"
 */
@RestController
public class ApplePayHelperController {

	@PostMapping("/applePaySessionInit")
	public ApplePayCreateJsSessionResponseDto createApplePayJsSession(String appleValidationUrlFromJs) {

		String responseFromApple = DirectApiApplePayHelper.createApplePayJsSession(appleValidationUrlFromJs);

		ApplePayCreateJsSessionResponseDto dto = new ApplePayCreateJsSessionResponseDto();
		dto.setResponseFromApple(responseFromApple);

		return dto;

	}

}
