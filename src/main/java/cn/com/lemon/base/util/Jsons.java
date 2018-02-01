package cn.com.lemon.base.util;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Static utility methods pertaining to {@code JSONS} primitives.
 * <p>
 * The base utility contain basic operate,order to data transfer json data
 * <p>
 * the tools base on {@code jackson}
 *
 * @author shellpo shih
 * @version 1.0
 */
public final class Jsons {
	private static final Logger LOG = LoggerFactory.getLogger(Jsons.class.getName());
	private static final ObjectMapper objectMapper = new ObjectMapper();

	private Jsons() {
	}

	static {
		objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
		objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, true);
	}

	/**
	 * Serialize generic Java objects into json data.
	 * 
	 * @param data
	 *            {@code Object} the java object
	 * @return {@code String} the json data
	 */
	public static String json(Object data) {
		return json(null, data);
	}

	/**
	 * Serialize generic Java objects into json data.
	 * <p>
	 * also, the data that support cross domain requests are provided
	 * 
	 * @param callback
	 *            {@code String} Cross-domain request identification
	 * @param data
	 *            {@code Object} the java object
	 * @return {@code String} the json data
	 */
	public static String json(String callback, Object data) {
		if (isNullOrEmpty(callback)) {
			try {
				return objectMapper.writeValueAsString(data);
			} catch (JsonProcessingException e) {
				LOG.error("The " + data.toString() + " process JSON error!", e);
			}
		} else {
			try {
				return callback + "(" + objectMapper.writeValueAsString(data) + ")";
			} catch (JsonProcessingException e) {
				LOG.error("The " + data.toString() + " process JSON error!", e);
			}
		}
		return null;
	}

	/**
	 * Reflect json data into Java objects.
	 * 
	 * @param data
	 *            {@code String} the json data
	 * @param classType
	 *            the java object
	 * @return {@code T} the java object
	 */
	public static <T> T unjson(String data, Class<T> classType) {
		try {
			return objectMapper.readValue(data, classType);
		} catch (JsonMappingException e) {
			LOG.error("The deserializer JSON to " + classType + " failed!", e);
		} catch (JsonParseException e) {
			LOG.error("The deserializer JSON to " + classType + " failed!", e);
		} catch (IOException e) {
			LOG.error("The deserializer JSON to " + classType + " failed!", e);
		}
		return null;
	}

	/**
	 * Reflect json data into Java objects.
	 * 
	 * @param data
	 *            {@code String} the json data
	 * @param typeReference
	 *            {@code TypeReference} This generic abstract class is used for
	 *            obtaining full generics type information
	 * @return {@code T} the java object
	 */
	public static <T> T unjson(String data, TypeReference<T> typeReference) {
		try {
			return objectMapper.readValue(data, typeReference);
		} catch (JsonMappingException e) {
			LOG.error("The deserializer JSON to " + typeReference + " failed!", e);
		} catch (JsonParseException e) {
			LOG.error("The deserializer JSON to " + typeReference + " failed!", e);
		} catch (IOException e) {
			LOG.error("The deserializer JSON to " + typeReference + " failed!", e);
		}
		return null;
	}
}
