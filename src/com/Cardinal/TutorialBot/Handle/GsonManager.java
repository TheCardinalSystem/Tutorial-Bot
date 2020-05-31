package com.Cardinal.TutorialBot.Handle;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Arrays;

import com.Cardinal.TutorialBot.Util.InputStreamUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GsonManager {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static JsonObject configuration;

	static {
		InputStream stream = GsonManager.class.getResourceAsStream("Config.json");
		String json = InputStreamUtils.toString(stream);
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		configuration = GSON.fromJson(json, JsonObject.class);
	}

	public static <T> T getConstant(String id, Type type) {
		String[] route = id.split("\\.");

		JsonElement element = configuration.get(route[0]);
		route = Arrays.copyOfRange(route, 1, route.length);
		for (String s : route) {
			element = element.getAsJsonObject().get(s);
		}
		return GSON.fromJson(element, type);
	}

}
