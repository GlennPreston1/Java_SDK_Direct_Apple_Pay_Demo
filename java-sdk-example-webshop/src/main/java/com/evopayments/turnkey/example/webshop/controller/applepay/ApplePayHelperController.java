package com.evopayments.turnkey.example.webshop.controller.applepay;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.evopayments.turnkey.config.TestConfig;
import com.evopayments.turnkey.example.webshop.data.applepay.ApplePayCreateJsSessionRequestDto;
import com.evopayments.turnkey.example.webshop.data.applepay.ApplePayCreateJsSessionResponseDto;
import com.evopayments.turnkey.util.apple.CreateApplePayJsSessionHelper;

/**
 * Relevant for the "Advanced/DirectAPI (Apple Pay) example"
 */
@RestController
public class ApplePayHelperController {

	private CreateApplePayJsSessionHelper createApplePayJsSessionHelper;

	@PostConstruct
	public void init() {
		createApplePayJsSessionHelper = new CreateApplePayJsSessionHelper(TestConfig.getInstance());
	}

	@PostMapping("/applePaySessionInit")
	public ApplePayCreateJsSessionResponseDto createApplePayJsSession(
			@RequestBody ApplePayCreateJsSessionRequestDto requestDto) {
		
		String responseFromApple = createApplePayJsSessionHelper.createApplePayJsSession(requestDto.getAppleValidationUrlFromJs());

		ApplePayCreateJsSessionResponseDto responseDto = new ApplePayCreateJsSessionResponseDto();
		responseDto.setResponseFromApple(responseFromApple);

		return responseDto;

	}

}
