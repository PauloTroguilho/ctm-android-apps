package com.ctm.eadvogado.util;

import java.io.IOException;
import java.io.StringReader;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;

public class JsonUtils {

	public static final JsonFactory JSON_FACTORY = new GsonFactory();
	public static final JsonObjectParser PARSER = new JsonObjectParser(JSON_FACTORY);
	
	
	/**
	 * Converte uma string json p/ um objeto da classe informada.
	 * 
	 * @param jsonString
	 * @param clazz
	 * @return
	 * @throws IOException 
	 */
	public static <T> T parse(String jsonString, Class<T> clazz) throws IOException {
		StringReader sReader = new StringReader(jsonString);
		return PARSER.parseAndClose(sReader, clazz);
	}
	
	/**
	 * Converte uma string json p/ um objeto da classe informada.
	 *  
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T parseQuietly(String jsonString, Class<T> clazz) {
		StringReader sReader = new StringReader(jsonString);
		try {
			return PARSER.parseAndClose(sReader, clazz);
		} catch (IOException e) {
		}
		return null;
	}

}
