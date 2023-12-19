package com.fireflink.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceWithEssentials {

	private String service;
	private Essentials essentials;
}
