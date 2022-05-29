package com.appresent.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilsConverter {
	private UtilsConverter() {}
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static String toJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
}
