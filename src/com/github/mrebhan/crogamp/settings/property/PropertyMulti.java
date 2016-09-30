package com.github.mrebhan.crogamp.settings.property;

import java.util.AbstractMap;
import java.util.Map.Entry;

import de.marco_rebhan.encodelib.IOStream;

public class PropertyMulti<T extends PropertyContainer> extends PropertyMap<T> {

	@SuppressWarnings("deprecation")
	@Override
	public void serializePart(IOStream stream, String s, T v) {
		stream.putString(s);
		stream.putString(v.getClass().getName());
		IOStream tempstream = new IOStream();
		v.serialize(tempstream);
		byte[] data = tempstream.getBytes();
		stream.putInt(data.length);
		stream.putRawByte(data);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Entry<String, T> deserializePart(IOStream stream) {
		String s = stream.getString();
		String className = stream.getString();
		int length = stream.getInt();
		try {
			Class<? extends T> c = (Class<? extends T>) getClass().getClassLoader().loadClass(className);
			T t = c.newInstance();
			t.deserialize(stream);
			return new AbstractMap.SimpleEntry(s, t);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			System.out.printf("Couldn't load property container class %s. Skipping%n", className);
			stream.skip(length);
		}
		return null;
	}

}
