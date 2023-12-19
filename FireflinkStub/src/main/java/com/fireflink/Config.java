package com.fireflink;

import java.util.Map;

import lombok.Data;

@Data
public class Config {
	
	
	private final String contentType = "application/json";
	private final String[] aadhars = {"111111111111","123456789123"};
	private final String[] keys = {"content-type","clientid","secretkey"};
	
	private final Map<String,String> uid_aadhar = Map.ofEntries(
			Map.entry("888888888888", "111111111111"),
			Map.entry("777777777777", "123456789123"));
	

}
