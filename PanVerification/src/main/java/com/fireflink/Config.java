package com.fireflink;

import java.util.Map;

import lombok.Data;

@Data
public class Config {
	
	
	private final String contentType = "application/json";
	private final String[] keys = {"content-type","clientid","secretkey"};
	
	private final Map<String, String> panWithStatus = Map.ofEntries(
			Map.entry("ABC123ABC", "E"),
			Map.entry("CDE234CDE", "F"),
			Map.entry("ASD321ERT", "X"),
			Map.entry("LKJ123MNJ", "D"),
			Map.entry("DHU234BVK", "N"),
			Map.entry("DES321OBA", "EA"),
			Map.entry("JSE143NVF", "EC"),
			Map.entry("RDE234CDZ", "ED"),
			Map.entry("AED321MCO", "EI"),
			Map.entry("NFR128PMK", "EL"),
			Map.entry("VCZ254ADP", "EM"),
			Map.entry("ASD321XXT", "EP"),
			Map.entry("OAPC821AGC", "ES"),
			Map.entry("ADE034CDE", "EU"),
			Map.entry("FSR301ECT", "I"));


	private final Map<String, String> aadharSeedingStatus =  Map.ofEntries(
			Map.entry("ABC123ABC", "Y"),
			Map.entry("CDE234CDE", " "),
			Map.entry("ASD321ERT", "R"),
			Map.entry("LKJ123MNJ", "NA"),
			Map.entry("DHU234BVK", " "),
			Map.entry("DES321OBA", "R"),
			Map.entry("JSE143NVF", "R"),
			Map.entry("RDE234CDZ", "NA"),
			Map.entry("AED321MCO", "NA"),
			Map.entry("NFR128PMK", "R"),
			Map.entry("VCZ254ADP", "NA"),
			Map.entry("ASD321XXT", "NA"),
			Map.entry("OAPC821AGC", "NA"),
			Map.entry("ADE034CDE", "NA"),
			Map.entry("FSR301ECT", "NA"));
	
	private final Map<String, String> panStatusCodes = Map.ofEntries(
			Map.entry( "E", "VALID"),
			Map.entry( "F", "Fake"),
			Map.entry( "X", "Deactivated"),
			Map.entry( "D", "Deleted"),
			Map.entry( "N", "Invalid"),
			Map.entry( "EA", "Amalgamation"),
			Map.entry( "EC", "Acquition"),
			Map.entry( "ED", "Death"),
			Map.entry( "EI", "Dissolution"),
			Map.entry( "EL", "Liquidited"),
			Map.entry( "EM", "Merger"),
			Map.entry( "EP", "Partition"),
			Map.entry( "ES", "Split"),
			Map.entry( "EU", "Under Liquidation"),
			Map.entry( "I", "Inoperative"));
	
	private final Map<String, String> aadharSeedingStatusCodes = Map.ofEntries(
			Map.entry( "Y", "Successfull"),
			Map.entry( "R", "UnSuccessfull"),
			Map.entry( " ", "not seeded"),
			Map.entry( "NA", "Not applicable"));
	
	private final Map<String, String> aadharToPan = Map.ofEntries(
			Map.entry( "111111111111", "ABC123ABC"),
			Map.entry( "123456789123", "ACZ254ARP"));
	private final Map<String, String> panToAadhar = Map.ofEntries(
			Map.entry("ABC123ABC", "111111111111"),
			Map.entry("ACZ254ARP","123456789123"));
	private final Map<String, String> pan_name = Map.ofEntries(
			Map.entry("BXMPA9842C", "NARESH AHIRWAR"));
	
}
