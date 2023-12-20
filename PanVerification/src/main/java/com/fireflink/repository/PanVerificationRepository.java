package com.fireflink.repository;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class PanVerificationRepository {
	
	public JsonNode panNameMatcher() {
		String resourcePath = "static/indivisualPan.json";
		return getJsonNode(resourcePath);
	}
	
	public JsonNode panWithAadharSeeding() {
	String resourcePath = "static/panwithadhaarseeding.json";
	return getJsonNode(resourcePath);
	
	}
	
	public JsonNode getFailedResponse() {
		String resourcePath = "static/failedResponse.json";
		return getJsonNode(resourcePath);
	}
	
	public JsonNode panPlusDetails() {
		String resourcePath = "static/panPlus.json";
		return getJsonNode(resourcePath);
	}

	public JsonNode aadharToPan() {
		String resourcePath = "static/aadharToPan.json";
		return getJsonNode(resourcePath);
	}

	public JsonNode panPro() {
		String resourcePath = "static/panPro.json";
		return getJsonNode(resourcePath);
	}

	public JsonNode aadhaarToMaskPanLite() {
		String resourcePath = "static/aadharToMaskPan.json";
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

	public JsonNode panToMaskAadhaarLite() {
		String resourcePath = "static/panToMaskAadhar.json";
		return getJsonNode(resourcePath);
	}

	
	
}
