package com.fireflink;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	public int getJsonLength(JsonNode jsonNode) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
            String jsonString = objectMapper.writeValueAsString(jsonNode);
            return jsonString.getBytes().length;
        } catch (Exception e) {
            // Handle exception if serialization fails
            e.printStackTrace();
            return -1; // Or any other appropriate value indicating failure
        }
	}

}
