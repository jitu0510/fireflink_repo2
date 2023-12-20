package com.fireflink.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fireflink.entities.AadharNumber;
import com.fireflink.entities.NameWithPan;
import com.fireflink.entities.PanNumber;
import com.fireflink.service.PanVerificationService;

@RestController
@RequestMapping("/")
public class PanVerificationController {

	@Autowired
	private PanVerificationService panVerificationService;
	
	@PostMapping("/panKyc/individualPan")
	public ResponseEntity<?> panNameMatcher(@RequestBody NameWithPan NameWithPan,
			@RequestHeader Map<String, String> headers){
		
		return panVerificationService.panNameMatcher(NameWithPan,headers);
	}
	
	@PostMapping("/invincible/panwithadhaarseeding")
	public ResponseEntity<?> panWithAadharSeeding(@RequestBody PanNumber panNumber,
			@RequestHeader Map<String, String> headers){
		return panVerificationService.panWithAadharSeeding(panNumber,headers);
	}
	
	@PostMapping("/invincible/panPlus")
	public ResponseEntity<?> panPlusDetails(@RequestBody PanNumber panNumber,
			@RequestHeader Map<String, String> headers){
		
		return panVerificationService.panPlusDetails(panNumber,headers);
	}
	
	@PostMapping("/invincible/aadhaarToPan")
	public ResponseEntity<?> aadharToPan(@RequestBody AadharNumber aadharNumber,
			@RequestHeader Map<String, String> headers){
		System.out.println(aadharNumber);
		return panVerificationService.aadharToPan(aadharNumber,headers);
	}
	
	@PostMapping("/invincible/panPro")
	public ResponseEntity<?> panPro(@RequestBody PanNumber panNumber,
			@RequestHeader Map<String, String> headers){
		return panVerificationService.panPro(panNumber,headers);
	}
	
	@PostMapping("/invincible/aadhaarToMaskPanLite")
	public ResponseEntity<?> aadhaarToMaskPanLite(@RequestBody AadharNumber aadharNumber,
			@RequestHeader Map<String, String> headers){
		return panVerificationService.aadhaarToMaskPanLite(aadharNumber,headers);
	}
	
	@PostMapping("/invincible/panToMaskAadhaarLite")
	public ResponseEntity<?> panToMaskAadhaarLite(@RequestBody PanNumber panNumber,
			@RequestHeader Map<String, String> headers){
		return panVerificationService.panToMaskAadhaarLite(panNumber,headers);
	}
	
	
}
