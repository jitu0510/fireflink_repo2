package com.fireflink.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fireflink.entities.AadharNumber;
import com.fireflink.entities.ClientIdWithOtp;
import com.fireflink.entities.Uid;
import com.fireflink.service.AadharService;

@RestController
@RequestMapping("/")
public class AadharValidationController {

	@Autowired
	private AadharService aadharService;
	
	
	
	@PostMapping("/aadhaarVerification/requestOtp")
	public ResponseEntity<?> requestOTP( @RequestBody AadharNumber aadharNumber,
			@RequestHeader Map<String, String> headers) throws IOException{
		return aadharService.requestOTP(aadharNumber,headers);
		
	}
	
	@PostMapping("/aadhaarVerification/submitOtp")
	public ResponseEntity<?> submitOtp(@RequestHeader Map<String, String> headers,
			@RequestBody ClientIdWithOtp clientIdWithOtp){
		 return aadharService.submitOtp(clientIdWithOtp,headers);
		
	}
	
	@PostMapping("/aadharVerification")
	public ResponseEntity<?> aadharVerification(@RequestBody Uid uid,
			@RequestHeader Map<String, String> headers){
		return aadharService.aadharVerification(uid,headers);
	}
	
	@PostMapping("/aadhaarValidation/V2")
	public ResponseEntity<?> aadharValidationV2(@RequestBody AadharNumber aadharNumber,
			@RequestHeader Map<String, String> headers){
		
		return  aadharService.aadharValidationV2(aadharNumber,headers);
	}
	
	@PostMapping("/aadhaarValidation/V3")
	public ResponseEntity<?> aadhaarValidationV3(@RequestBody AadharNumber aadharNumber,
			@RequestHeader Map<String, String> headers){
		return aadharService.aadharValidationV3(aadharNumber, headers);
	}
	
}



