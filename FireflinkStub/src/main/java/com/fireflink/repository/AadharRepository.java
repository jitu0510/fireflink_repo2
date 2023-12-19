package com.fireflink.repository;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class AadharRepository {
	
	public JsonNode requestOTP() {
		 String resourcePath = "static/requestOtp.json";
	     return getJsonNode(resourcePath);
	}

	public JsonNode submitOtp() {
		 String resourcePath = "static/submitOtp.json";
		 return getJsonNode(resourcePath); 
	}

	public JsonNode aadharValidationV2() {
		 String resourcePath = "static/aadhaarValidation_V2.json";
		 return getJsonNode(resourcePath);
	}

	public JsonNode aadharValidationV3() {
		String resourcePath = "static/aadhaarValidation_V3.json";
		return getJsonNode(resourcePath);
	}

	public JsonNode aadharVerification() {
		String resourcePath = "static/aadharVerification.json";
		return getJsonNode(resourcePath);
	}
	
	public JsonNode getJsonNode(String fileName) {
		//String resourcePath = "static/aadharVerification.json";
		ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode=null;
        try {
             jsonNode = objectMapper.readTree(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(jsonNode);
        
        return jsonNode;
	}

}
