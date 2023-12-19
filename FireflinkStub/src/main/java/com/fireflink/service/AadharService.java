package com.fireflink.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import com.fireflink.JsonUtil;
import com.fireflink.PropertiesConfiguration;
import com.fireflink.entities.AadharNumber;
import com.fireflink.entities.ClientIdWithOtp;
import com.fireflink.entities.Uid;
import com.fireflink.repository.AadharRepository;

@Service
public class AadharService {

	@Autowired
	private AadharRepository aadharRepository;

	@Autowired
	private Properties properties ; // = new PropertiesConfiguration().getProperties();

	public ResponseEntity<?> requestOTP(AadharNumber aadharNumber, Map<String, String> headers) {

		Config config = new Config();

		if (verifyClientId(headers)) {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Client_Id");
		}
		if (verifySecretKey(headers)) {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Secret_Key");
		}
		List<String> aadhars = Arrays.asList(config.getAadhars());
		if (!aadhars.contains(aadharNumber.getAadhaarNumber())) {
			return ResponseEntity.status(HttpStatusCode.valueOf(400)).contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Data");
		}
		JsonNode rootNode = aadharRepository.requestOTP();
		ObjectMapper mapper = new ObjectMapper();

		JsonNode dataNode = rootNode.get("result").get("data");
		System.out.println(dataNode);
		if (dataNode.isObject()) {
			ObjectNode objectNode = (ObjectNode) dataNode;
			// Modify existing values or add new ones
			objectNode.put("client_id", properties.getProperty("client_id")); // Update existing value
			// objectNode.put("city", config.getSECRET_KEY()); // Add a new field

			// Convert the modified ObjectNode back to JSON string
			String updatedJsonString = null;
			try {
				updatedJsonString = mapper.writeValueAsString(objectNode);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			System.out.println(objectNode);
			System.out.println("Updated JSON: " + updatedJsonString);
		}
		int length = new JsonUtil().getJsonLength(rootNode);
		System.out.println("Length is " + length);
		System.out.println(rootNode);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).contentLength(length)
				.body(rootNode);

	}

	public ResponseEntity<?> submitOtp(ClientIdWithOtp clientIdWithOtp, Map<String, String> headers) {

		if (verifyClientId(headers)) {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Client_Id");
		}
		if (verifySecretKey(headers)) {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Secret_Key");
		}
		if (!(clientIdWithOtp.getClient_id().equals(properties.getProperty("client_id"))
				&& clientIdWithOtp.getOtp().equals(properties.getProperty("otp")))) {
			return ResponseEntity.status(HttpStatusCode.valueOf(400)).contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Data");
		}

		JsonNode rootNode = aadharRepository.submitOtp();

		ObjectMapper mapper = new ObjectMapper();

		JsonNode dataNode = rootNode.get("result").get("data");
		System.out.println(dataNode);
		if (dataNode.isObject()) {
			ObjectNode objectNode = (ObjectNode) dataNode;

			// Modify existing values or add new ones
			objectNode.put("client_id", properties.getProperty("client_id")); // Update existing value

			// objectNode.put("city", config.getSECRET_KEY()); // Add a new field

			// Convert the modified ObjectNode back to JSON string
			String updatedJsonString = null;
			try {
				updatedJsonString = mapper.writeValueAsString(objectNode);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			System.out.println(objectNode);
			System.out.println("Updated JSON: " + updatedJsonString);
		}
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.contentLength(new JsonUtil().getJsonLength(rootNode)).body(rootNode);

	}

	public ResponseEntity<?> aadharValidationV2(AadharNumber aadharNumber, Map<String, String> headers) {
		Config config = new Config();
		if (verifyClientId(headers)) {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Client_Id");
		}
		if (verifySecretKey(headers)) {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Secret_Key");
		}
		List<String> aadhars = Arrays.asList(config.getAadhars());
		if (!aadhars.contains(aadharNumber.getAadhaarNumber())) {
			return ResponseEntity.status(HttpStatusCode.valueOf(400)).contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Data");
		}
		JsonNode rootNode = aadharRepository.aadharValidationV2();

		ObjectMapper mapper = new ObjectMapper();

		JsonNode dataNode = rootNode.get("result").get("data");
		System.out.println(dataNode);
		if (dataNode.isObject()) {
			ObjectNode objectNode = (ObjectNode) dataNode;

			// Modify existing values or add new ones
			objectNode.put("aadhaar_number", aadharNumber.getAadhaarNumber()); // Update existing value

			// Convert the modified ObjectNode back to JSON string
			String updatedJsonString = null;
			try {
				updatedJsonString = mapper.writeValueAsString(objectNode);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			System.out.println(objectNode);
			System.out.println("Updated JSON: " + updatedJsonString);
		}

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.contentLength(new JsonUtil().getJsonLength(rootNode)).body(rootNode);
	}

	public ResponseEntity<?> aadharValidationV3(AadharNumber aadharNumber, Map<String, String> headers) {
		Config config = new Config();
		if (verifyClientId(headers)) {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Client_Id");
		}
		if (verifySecretKey(headers)) {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Secret_Key");
		}
		List<String> aadhars = Arrays.asList(config.getAadhars());
		if (!aadhars.contains(aadharNumber.getAadhaarNumber())) {
			return ResponseEntity.status(HttpStatusCode.valueOf(400)).contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Data");
		}
		JsonNode rootNode = aadharRepository.aadharValidationV3();
		if (rootNode == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body("Something went Wrong");
		}
		ObjectMapper mapper = new ObjectMapper();

		JsonNode dataNode = rootNode.get("result").get("data");
		System.out.println(dataNode);
		if (dataNode.isObject()) {
			ObjectNode objectNode = (ObjectNode) dataNode;

			// Modify existing values or add new ones
			objectNode.put("aadhaar_number", aadharNumber.getAadhaarNumber()); // Update existing value

			// Convert the modified ObjectNode back to JSON string
			String updatedJsonString = null;
			try {
				updatedJsonString = mapper.writeValueAsString(objectNode);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			System.out.println(objectNode);
			System.out.println("Updated JSON: " + updatedJsonString);
		}

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.contentLength(new JsonUtil().getJsonLength(rootNode)).body(rootNode);

	}

	public ResponseEntity<?> aadharVerification(Uid uid, Map<String, String> headers) {

		Config config = new Config();
		if (verifyClientId(headers)) {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Client_Id");
		}
		if (verifySecretKey(headers)) {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).contentType(MediaType.APPLICATION_JSON)
					.body("Invalid Secret_Key");
		}
		/*
		 * try { System.out.println("Checking"); String uid1 =
		 * config.getUid_aadhar().get(uid.getUid()); Long.parseLong(uid1);
		 * 
		 * }catch (Exception e) { return
		 * ResponseEntity.status(HttpStatusCode.valueOf(103))
		 * .body("UID must be a Number"); }
		 */
		String aadharNumber = "";
		if (config.getUid_aadhar().containsKey(uid.getUid())) {
			aadharNumber = config.getUid_aadhar().get(uid.getUid());
		} else {
			return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Invalid UID");
		}

		JsonNode rootNode = aadharRepository.aadharVerification();

		if (rootNode == null) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong");
		}
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.contentLength(new JsonUtil().getJsonLength(rootNode)).body(rootNode);
	}

	public boolean verifyClientId(Map<String, String> headers) {
		Config config = new Config();
		String[] keys = config.getKeys();

		if (headers.get(keys[1]).equals(properties.getProperty("client_id"))) {
			return false;
		}
		return true;

	}

	public boolean verifySecretKey(Map<String, String> headers) {
		Config config = new Config();
		String[] keys = config.getKeys();
		if (headers.get(keys[2]).equals(properties.getProperty("secret_key"))) {
			return false;
		}
		return true;
	}

}
