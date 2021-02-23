package com.mpf.website.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

@Slf4j
public class JsonUtil {

	private ObjectMapper objectMapper = null;

	/**
	 *
	 */
	public JsonUtil() {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		//objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
	}

	public <T> T readValue(String jsonStr, Class<T> valueType) {
		try {
			return objectMapper.readValue(jsonStr, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * json数组转List
	 * @param jsonStr
	 * @param valueTypeRef
	 * @return
	 */
	public  <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef){
		try {
			return objectMapper.readValue(jsonStr, valueTypeRef);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 *
	 * @param jsonStr
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public  String getKeyValue(String jsonStr, String key) throws IOException {
		if(key == null || key.isEmpty()) {
			if(log.isInfoEnabled()) {
				log.info("解析的字符串不能为空或者NULL");
			}
			return null;
		}

		JsonFactory jasonFactory = new JsonFactory();
		JsonParser jsonParser = jasonFactory.createParser(jsonStr);

		while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
			String fieldName = jsonParser.getCurrentName();
			if (key.equals(fieldName)) {
				jsonParser.nextToken();
				return jsonParser.getText();
			}
		}
		return null;
	}

	/**
	 *
	 * @param jsonStr
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public  String parseKeyValue(String jsonStr, String key) throws IOException {
		if(jsonStr == null || jsonStr.isEmpty()) {
			if(log.isInfoEnabled()) {
				log.info("解析的字符串不能为空或者NULL");
			}
			return null;
		}

		JsonFactory jsonFactory = new JsonFactory();
		JsonParser jsonParser = jsonFactory.createParser(jsonStr);

		int startCount = 1;
		while (startCount > 0) {
			jsonParser.nextToken();
			String fieldName = jsonParser.getCurrentName();
			if (key.equals(fieldName)) {
				jsonParser.nextToken();
				return jsonParser.getText();
			}

			if(jsonParser.currentToken() == JsonToken.START_OBJECT) {
				startCount++;
			} else if(jsonParser.currentToken() == JsonToken.END_OBJECT) {
				startCount--;
			}
		}
		return null;
	}

	/**
	 *
	 * @param jsonStr
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public  String getKeyValueNode(String jsonStr, String key) throws IOException {
		if(key == null || key.isEmpty()) {
			return null;
		}

		JsonNode node = objectMapper.readTree(jsonStr);
		JsonNode resultNode = node.get(key);
		if(!resultNode.isNull()){
			return objectMapper.writeValueAsString(resultNode);
		}

		return null;
	}

	/**
	 * 把JavaBean转换为json字符串
	 *
	 * @param object
	 * @return
	 */
	public  String toJSon(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ObjectMapper getObjectMapper() {
		if(ObjectUtils.isEmpty(objectMapper)) {
			objectMapper = new ObjectMapper();
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		}

		return objectMapper;
	}

	public static String getJsonFromResource(String filename){
		String json = null;
		try{
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
			if (inputStream != null){
				StringBuilder sb = new StringBuilder();
				InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
				BufferedReader bfReader = new BufferedReader(reader);
				String content = null;
				while((content = bfReader.readLine()) != null){
					sb.append(content);
				}
				bfReader.close();
				json = sb.toString();
			}else{
				log.info("[{}] file not exist",filename);
			}

		}catch (Exception e){
			log.error("read file to string error",e);
		}

		return json;
	}

}
