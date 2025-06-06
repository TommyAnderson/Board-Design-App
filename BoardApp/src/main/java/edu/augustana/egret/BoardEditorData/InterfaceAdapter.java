package edu.augustana.egret.BoardEditorData;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

// All credits to this class go to André Carvalho on stack overflow
//  https://stackoverflow.com/questions/16000163/using-gson-and-abstract-classes


public class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

	/**
	 * Creates a new JsonObject to which properties are applied and into which the
	 * data from the passed object is stored.
	 * 
	 * @param object        - the object containing data to serialize
	 * @param interfaceType - the behaviors allowed by the interface type
	 * @param context       - the context which the passed object is serialized into
	 * @return the created JsonObject with data added to it
	 */

	@Override
	public final JsonElement serialize(final T object, final Type interfaceType,
			final JsonSerializationContext context) {
		final JsonObject member = new JsonObject();

		member.addProperty("type", object.getClass().getName());

		member.add("data", context.serialize(object));

		return member;
	}

	/**
	 * Creates a new JsonObject to which properties are applied and into which data
	 * is stored from the passed JsonElement elem.
	 * 
	 * @param elem          - the object containing data to deserialize
	 * @param interfaceType - the behaviors allowed by the interface type
	 * @param context       - the context which the passed object is serialized from
	 * @return deserialized data from the JsonElement elem
	 * @throws JsonParseException - thrown if the JsonElement passed has a format
	 *                            error
	 */

	@Override
	public final T deserialize(final JsonElement elem, final Type interfaceType,
			final JsonDeserializationContext context) throws JsonParseException {
		final JsonObject member = (JsonObject) elem;
		final JsonElement typeString = get(member, "type");
		final JsonElement data = get(member, "data");
		final Type actualType = typeForName(typeString);

		return context.deserialize(data, actualType);
	}

	private Type typeForName(final JsonElement typeElem) {
		try {
			return Class.forName(typeElem.getAsString());
		} catch (ClassNotFoundException e) {
			throw new JsonParseException(e);
		}
	}

	private JsonElement get(final JsonObject wrapper, final String memberName) {
		final JsonElement elem = wrapper.get(memberName);

		if (elem == null) {
			throw new JsonParseException("no '" + memberName + "' member found in json file.");
		}
		return elem;
	}

}
