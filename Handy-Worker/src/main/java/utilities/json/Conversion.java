
package utilities.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Conversion {

	/**
	 * Se le pasa un objeto y lo convierte a un json
	 * 
	 * @param json
	 * @return clase
	 */
	public static String objectToJson(final Object obj) {
		/*
		 * Primero, convertiremos la clase en un json
		 */
		final ObjectMapper mapperclass = new ObjectMapper();
		mapperclass.enable(SerializationFeature.INDENT_OUTPUT);
		mapperclass.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		String jsonInString = null;
		try {
			jsonInString = mapperclass.writeValueAsString(obj);
		} catch (final JsonProcessingException e1) {
			System.err.println(e1.toString());
		}
		return jsonInString;

	}

	/**
	 * Se le pasa un json y lo convierte a un objeto
	 * 
	 * @param json
	 * @return clase
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static Object jsonToObject(final String json, final Class<?> cls) {
		/*
		 * Ahora convertiremos un json en la clase pertinente
		 */
		final ObjectMapper mapperjson = new ObjectMapper();
		mapperjson.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		Object c = null;
		//c = (cls.getClass());

		@SuppressWarnings("rawtypes")
		Class cl = null;
		try {
			cl = Class.forName(cls.getName());
		} catch (final ClassNotFoundException e1) {
			System.err.println(e1.toString());
		}

		try {
			c = mapperjson.readValue(json, cl);
		} catch (final JsonParseException e) {
			System.err.println(e.toString());
		} catch (final JsonMappingException e) {
			System.err.println(e.toString());
		} catch (final IOException e) {
			System.err.println(e.toString());
		}
		return cl.cast(c);
	}
}
