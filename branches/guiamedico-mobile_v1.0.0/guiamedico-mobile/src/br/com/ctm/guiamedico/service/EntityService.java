package br.com.ctm.guiamedico.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.ctm.guiamedico.model.BaseEntity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public abstract class EntityService<E extends BaseEntity> {

	private static final String RESOURCE_BASE_URL = "http://192.168.1.5:8080/guiamedico/rest/";

	private String resourceUrl;
	private Class<E> clazz;

	/**
	 * @param entityResourceName
	 */
	public EntityService(String entityResourceName, Class<E> clazz) {
		super();
		this.resourceUrl = RESOURCE_BASE_URL + entityResourceName;
		this.clazz = clazz;
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public List<E> findAll() throws IOException {
		List<E> result = new ArrayList<E>();
		String contents = RESTfulClient.getContents(this.resourceUrl);

		JsonElement json = new JsonParser().parse(contents);
		JsonArray jsArray = json.getAsJsonArray();
		Iterator<JsonElement> it = jsArray.iterator();
		Gson gson = new Gson();
		
		while (it.hasNext()) {
			JsonElement json2 = it.next();
			E item = gson.fromJson(json2, clazz);
			result.add(item);
		}
		return result;
	}

}
