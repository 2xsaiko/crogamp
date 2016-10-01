package com.github.mrebhan.crogamp.settings.property;

import java.util.HashMap;
import java.util.Map;

import de.marco_rebhan.encodelib.IOStream;

public abstract class PropertyContainer {

	private Map<Property<?>, Object> properties;

	public PropertyContainer() {
		properties = new HashMap<>();
		setDefaults();
	}

	public void serialize(IOStream stream) {
		stream.putInt(properties.size());
		properties.forEach((p, v) -> serializeProperty(stream, p, v));
	}

	public void deserialize(IOStream stream) {
		int size = stream.getInt();
		for (int i = 0; i < size; i++) {
			String pname = stream.getString();
			int proplength = stream.getInt();
			boolean flag = false;
			for (Property<?> prop : properties.keySet()) {
				if (pname.equals(prop.getName())) {
					flag = true;
					properties.put(prop, prop.deserialize(stream));
					break;
				}
			}
			if (!flag) {
				System.out.printf("Warning: Property %s couldn't be found for class %s. Skipping!%n", pname, getClass().getSimpleName());
				stream.skip(proplength);
			}
		}
	}

	public <T> void setValue(Property<T> prop, T value) {
		properties.put(prop, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(Property<T> prop) {
		if (properties.containsKey(prop)) {
			return (T) properties.get(prop);
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private <T> void serializeProperty(IOStream stream, Property<T> prop, Object v) {
		if (prop.getName() == null) {
			System.out.println("FUK U");
		}
		stream.putString(prop.getName());
		IOStream tempstream = new IOStream(true);
		prop.serialize(tempstream, (T) v);
		byte[] bytes = tempstream.getBytes();
		stream.putInt(bytes.length);
		stream.putRawByte(bytes);
	}

	protected abstract void setDefaults();

}
