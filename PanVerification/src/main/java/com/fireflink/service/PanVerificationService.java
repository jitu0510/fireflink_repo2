package com.fireflink.service;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fireflink.Config;
import com.fireflink.entities.AadharNumber;
import com.fireflink.entities.NameWithPan;
import com.fireflink.entities.PanNumber;
import com.fireflink.repository.PanVerificationRepository;

@Service
public class PanVerificationService {
	
	@Autowired
	private PanVerificationRepository repository;
	
	@Autowired
	private Properties properties;

	public ResponseEntity<?> panNameMatcher(NameWithPan nameWithPan, Map<String, String> headers) {

		Config config = new Config();
		System.out.println(headers);
		System.out.println(properties.getProperty("client_id"));
		
		if(verifyCleintId(headers) || verifySecretKey(headers)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(repository.getFailedResponse());
		}
		
		if(!verifyPanPattern(nameWithPan.getPan().toUpperCase())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Pan Number");
		}
		
		if(!config.getPan_name().containsKey(nameWithPan.getPan().toUpperCase())) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.contentType(MediaType.APPLICATION_JSON)
					.body(null);
		}
		
		
		JsonNode jsonNode = repository.panNameMatcher(); 
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(jsonNode);
		
	
	}

	public ResponseEntity<?> panWithAadharSeeding(PanNumber pan, Map<String, String> headers) {
		Config config = new Config();
		System.out.println(headers);
		if(verifyCleintId(headers) || verifySecretKey(headers)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(repository.getFailedResponse());
		}
		String panNumber = pan.getPanNumber().toUpperCase();
		if(!config.getPanWithStatus().containsKey(panNumber.toUpperCase())) {
			return ResponseEntity.status(HttpStatusCode.valueOf(400)).
					body("Pan Number Not Found"); 
		}
		String panStatus = config.getPanWithStatus().get(panNumber);
		String aadharSeedingStuatus = config.getAadharSeedingStatus().get(panNumber);
		JsonNode rootNode = repository.panWithAadharSeeding();
		System.out.println(rootNode);
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode panDetailsDataObject = rootNode.get("result").get("Succeeded").get("pan_Details").get("data");
		
		System.out.println(panDetailsDataObject);
		if (panDetailsDataObject.isObject()) {
            ObjectNode objectNode = (ObjectNode) panDetailsDataObject;

            // Modify existing values or add new ones
           // objectNode.put("client_id", config.getCLIENT_ID()); // Update existing value
            if(config.getPanWithStatus().get(panNumber.toUpperCase()).equals("E") && config.getAadharSeedingStatus().get(panNumber.toUpperCase()).equals("Y") ) {
            	objectNode.put("number", panNumber);
            }else {
            	objectNode.put("number", panNumber.toUpperCase());
            	objectNode.put("pan_status",config.getPanWithStatus().get(panNumber) );
            	objectNode.put("pan_status_code",config.getPanStatusCodes().get(config.getPanWithStatus().get(panNumber)) );
            	objectNode.put("aadhaar_seeding_status",config.getAadharSeedingStatus().get(panNumber) );
            	objectNode.put("aadhaar_seeding_status_code",config.getAadharSeedingStatusCodes().get(config.getAadharSeedingStatus().get(panNumber)) );
            	objectNode.put("name", "");
            	objectNode.put("type_of_holder", "");
            	objectNode.put("type_of_holder_code", "");
            	objectNode.put("is_individual", "");
            	objectNode.put("is_valid", "false");
            	objectNode.put("first_name", "");
            	
            	objectNode.put("middle_name", "");
            	objectNode.put("last_name", "");
            	objectNode.put("title", "");
            	objectNode.put("last_updated_on", "");
            	
            }
            String updatedJsonString = null;
			try {
				updatedJsonString = mapper.writeValueAsString(objectNode);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			System.out.println(objectNode);
            System.out.println("Updated JSON: " + updatedJsonString);
        }
		
		
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(rootNode);
		
	}

	public ResponseEntity<?> panPlusDetails(PanNumber pan, Map<String, String> headers) {
		Config config = new Config();
		
		System.out.println(headers);
		if(verifyCleintId(headers) || verifySecretKey(headers)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(repository.getFailedResponse());
		}
		
		JsonNode rootNode = repository.panPlusDetails();
		System.out.println(rootNode);
		String panNumber = pan.getPanNumber();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode resultObject = rootNode.get("result");
		if(config.getPanWithStatus().containsKey(panNumber.toUpperCase()) ) {
			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(rootNode);
		}
		if (resultObject.isObject()) {
            ObjectNode objectNode = (ObjectNode) resultObject;
		
			objectNode.put("FIRST_NAME", "");
			objectNode.put("MIDDLE_NAME", "");
			objectNode.put("LAST_NAME", "");
			objectNode.put("AADHAR_NUM", "");
			objectNode.put("AADHAR_LINKED", "");
			objectNode.put("DOB_VERIFIED", "");
			objectNode.put("DOB_CHECK", "");
			objectNode.put("EMAIL", "");
			objectNode.put("DOB", "");
			objectNode.put("GENDER", "");
			objectNode.put("IDENTITY_TYPE", "");
			objectNode.put("MOBILE_NO", "");
			objectNode.put("ADDRESS_1", "");
			objectNode.put("ADDRESS_2", "");
			objectNode.put("ADDRESS_3", "");
			objectNode.put("PINCODE", "");
			objectNode.put("CITY", "");
			objectNode.put("STATE", "");
			objectNode.put("COUNTRY", "");
		
		String updatedJsonString = null;
		try {
			updatedJsonString = mapper.writeValueAsString(objectNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(objectNode);
        System.out.println("Updated JSON: " + updatedJsonString);
        
	}
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(rootNode);
}

	public ResponseEntity<?> aadharToPan(AadharNumber aadharNumber, Map<String, String> headers) {
		Config config = new Config();
		if(verifyCleintId(headers) || verifySecretKey(headers)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(repository.getFailedResponse());
		}
		JsonNode rootNode = repository.aadharToPan();
		
		if(config.getAadharToPan().containsKey(aadharNumber.getAadharNumber().toUpperCase())) {
			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(rootNode);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode resultObject = rootNode.get("result");
		JsonNode dataObject = rootNode.get("result").get("data");
		
		if(resultObject.isObject() && dataObject.isObject()) {
			ObjectNode resultNode = (ObjectNode)resultObject;
			ObjectNode dataNode = (ObjectNode) dataObject;
			dataNode.put("pan_number", "");
			resultNode.put("status_code", 400);
			resultNode.put("success", false);
			resultNode.put("message", "UnSuccessfull");
			resultNode.put("message_code", "UnSuccessfull");
			try {
				mapper.writeValueAsString(dataNode);
				mapper.writeValueAsString(resultNode);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(rootNode);
		
	}

	public ResponseEntity<?> panPro(PanNumber panNumber, Map<String, String> headers) {
		Config config = new Config();
		if(verifyCleintId(headers) || verifySecretKey(headers)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(repository.getFailedResponse());
		}
		JsonNode rootNode = repository.panPro();
		
		if(config.getPanWithStatus().containsKey(panNumber.getPanNumber().toUpperCase()) &&(! config.getPanWithStatus().get(panNumber.getPanNumber().toUpperCase()).equals("N"))) {
			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(rootNode);
		}
		ObjectMapper mapper = new ObjectMapper();
		JsonNode resultObject = rootNode.get("result");
		JsonNode dataObject = rootNode.get("result").get("data");
		
		if(resultObject.isObject() && dataObject.isObject()) {
			ObjectNode resultNode = (ObjectNode) resultObject;
			ObjectNode dataNode = (ObjectNode) dataObject;
			ObjectNode responseNode = (ObjectNode) rootNode;
			resultNode.put("status_code", 400);
			resultNode.put("success", false);
			resultNode.put("message_code", "Failed");
			
			dataNode.put("pan_number", "");
			dataNode.put("father_name", "");
			dataNode.put("full_name", "");
			dataNode.put("dob_verified", "");
			dataNode.put("less_info", "");
			dataNode.put("dob_check", "");
			dataNode.put("category", "");
			
			responseNode.put("message", "Data Not Found");
			
		
			try {
//				mapper.writeValueAsString(dataNode);
//				mapper.writeValueAsString(resultNode);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(rootNode);
	}

	public ResponseEntity<?> aadhaarToMaskPanLite(AadharNumber aadharNumber, Map<String, String> headers) {
		Config config = new Config();
		String[] keys = config.getKeys();
		if(verifyCleintId(headers) || verifySecretKey(headers)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(repository.getFailedResponse());
		}
		JsonNode rootNode = repository.aadhaarToMaskPanLite();
		JsonNode resultNode = rootNode.get("result");
		if(config.getAadharToPan().containsKey(aadharNumber.getAadharNumber().toUpperCase())) {
			ObjectNode resultObject = (ObjectNode) resultNode;
			String panNumber = config.getAadharToPan().get(aadharNumber.getAadharNumber().toUpperCase());
			String pan = "";
			for(int i=0;i<panNumber.length();i++) {
				if(i<2 || i>=(panNumber.length()-2)) {
					pan+=panNumber.charAt(i);
				}else {
					pan+="x";
				}
			}
			resultObject.put("pan", pan);
			
			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(rootNode);
		}
		
		ObjectNode responseObject = (ObjectNode) rootNode;
		ObjectNode resultObject = (ObjectNode) resultNode;
		responseObject.put("message", "Data Not Found");
		resultObject.put("pan", "");
		
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(rootNode);
		
		
		
	}

	public ResponseEntity<?> panToMaskAadhaarLite(PanNumber panNumber, Map<String, String> headers) {
		Config config = new Config();
		
		if(verifyCleintId(headers) || verifySecretKey(headers)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(repository.getFailedResponse());
		}
		JsonNode rootNode = repository.panToMaskAadhaarLite();
		JsonNode resultNode = rootNode.get("result");
		if(config.getPanToAadhar().containsKey(panNumber.getPanNumber().toUpperCase())) {
			ObjectNode resultObject = (ObjectNode) resultNode;
			String aadharNumber = config.getPanToAadhar().get(panNumber.getPanNumber().toUpperCase());
			String aadhar = "";
			for(int i=0;i<aadharNumber.length();i++) {
				if(i<2 || i>=(aadharNumber.length()-2)) {
					aadhar+=aadharNumber.charAt(i);
				}else {
					aadhar+="x";
				}
			}
			resultObject.put("aadhaar", aadhar);
			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(rootNode);
		}
		
		ObjectNode resultObject = (ObjectNode) resultNode;
		ObjectNode responseObject = (ObjectNode) rootNode;
		resultObject.put("aadhaar", "");
		responseObject.put("message", "Data Not Found");
		
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(rootNode);
	}
	
	public boolean verifyCleintId(Map<String, String> headers){
		Config config = new Config();
		String[] keys = config.getKeys();
		
		if(headers.get(keys[1]).equals(properties.getProperty("client_id"))) {
			return false;
		}
		return true;
		
	}
	
	public boolean verifySecretKey(Map<String, String> headers) {
		Config config = new Config();
		String[] keys = config.getKeys();
		if(headers.get(keys[2]).equals(properties.getProperty("secret_key"))) {
			return false;
		}
		return true;
	}
	
	public boolean verifyPanPattern(String panNumber) {
		Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
		   
		Matcher matcher = pattern.matcher(panNumber);
		// Check if pattern matches 
		if (matcher.matches()) 
		  return true;
		 
		return false;
	}
	
}
