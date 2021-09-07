package com.evopayments.turnkey.example.webshop.controller.applepay;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.evopayments.turnkey.example.webshop.data.applepay.ApplePayCreateJsSessionRequestDto;
import com.evopayments.turnkey.example.webshop.data.applepay.ApplePayCreateJsSessionResponseDto;
import com.evopayments.turnkey.util.DirectApiApplePayHelper;

/**
 * Relevant for the "Advanced/DirectAPI (Apple Pay) example"
 */
@RestController
public class ApplePayHelperController {

	@PostMapping("/applePaySessionInit")
	public ApplePayCreateJsSessionResponseDto createApplePayJsSession(@RequestBody ApplePayCreateJsSessionRequestDto requestDto) {

		String responseFromApple = DirectApiApplePayHelper.createApplePayJsSession(requestDto.getAppleValidationUrlFromJs());

		ApplePayCreateJsSessionResponseDto responseDto = new ApplePayCreateJsSessionResponseDto();
		responseDto.setResponseFromApple(responseFromApple);

		return responseDto;

	}

}
