package com.github.mrebhan.crogamp.settings.property;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.marco_rebhan.encodelib.IOStream;

public abstract class PropertyMap<V> extends Property<Map<String, V>> {

	@Override
	public void serialize(IOStream stream, Map<String, V> t) {
		stream.putInt(t.size());
		t.forEach((s, v) -> serializePart(stream, s, v));
	}

	public abstract void serializePart(IOStream stream, String s, V v);

	@Override
	public Map<String, V> deserialize(IOStream stream) {
		Map<String, V> map = new HashMap<>();
		int len = stream.getInt();
		for (int i = 0; i < len; i++) {
			Entry<String, V> e = deserializePart(stream);
			if (e != null) {
				map.put(e.getKey(), e.getValue());
			}
		}
		return map;
	}

	public abstract Entry<String, V> deserializePart(IOStream stream);

	public static Property<Map<String, String>> createPropertyString(String name) {
		return new PropertyMap<String>() {

			@Override
			public void serializePart(IOStream stream, String s, String v) {
				stream.putString(s);
				stream.putString(v);
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Entry<String, String> deserializePart(IOStream stream) {
				String s = stream.getString();
				String v = stream.getString();
				return new AbstractMap.SimpleEntry(s, v);
			}

		}.withName(name);
	}

	public static Property<Map<String, byte[]>> createPropertyByteArray(String name) {
		return new PropertyMap<byte[]>() {

			@Override
			public void serializePart(IOStream stream, String s, byte[] v) {
				stream.putString(s);
				stream.putInt(v.length);
				stream.putRawByte(v);
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Entry<String, byte[]> deserializePart(IOStream stream) {
				String s = stream.getString();
				int length = stream.getInt();
				byte[] b = new byte[length];
				for (int i = 0; i < b.length; i++) {
					b[i] = stream.getByte();
				}
				return new AbstractMap.SimpleEntry(s, b);
			}

		}.withName(name);
	}

}
