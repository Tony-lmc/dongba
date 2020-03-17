package com.jt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

//redis 对象与json的转化
public class ObjectMapperUtil {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	//对象转json方法
	public static String toJSON(Object target) {
		String json = null;
		try {
			json = MAPPER.writeValueAsString(target);
		} catch (Exception e) {
			e.printStackTrace();
			//将检查异常转化为运行时异常
			throw new RuntimeException();
		}
		return json;
	}

	//json转对象方法
	
	public static <T> T toObject(String json,Class<T> targetClass) {
		T target = null;
		try {
			target = MAPPER.readValue(json, targetClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return target;
	}

}
