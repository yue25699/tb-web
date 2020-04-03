package com.tb.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//编辑工具类实现对象与json转化
public class ObjectMapperUtil {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	public static String toJSON(Object obj) {
		String json =null;
		try {
			json=MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return json;
	}
	public static <T> T toObject(String json,Class<T> t){
		T readValue=null;
		try {
			 readValue = MAPPER.readValue(json, t);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return readValue;
		
	}
 }
