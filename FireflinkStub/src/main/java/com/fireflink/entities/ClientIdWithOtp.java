package com.fireflink.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientIdWithOtp {
	
	private String client_id;
	private String otp;

}
