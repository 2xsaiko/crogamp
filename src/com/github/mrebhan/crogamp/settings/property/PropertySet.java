package com.github.mrebhan.crogamp.settings.property;

import java.util.HashSet;
import java.util.Set;

import de.marco_rebhan.encodelib.IOStream;

public abstract class PropertySet<E> extends Property<Set<E>> {

	@Override
	public void serialize(IOStream stream, Set<E> t) {
		stream.putInt(t.size());
		t.forEach(e -> serializePart(stream, e));
	}

	public abstract void serializePart(IOStream stream, E e);
	
	@Override
	public Set<E> deserialize(IOStream stream) {
		HashSet<E> set = new HashSet<>();
		int len = stream.getInt();
		for (int i = 0; i < len; i++) {
			E e = deserializePart(stream);
			if (e != null) {
				set.add(e);
			}
		}
		return set;
	}
	
	public abstract E deserializePart(IOStream stream);
	
	public static Property<Set<String>> createProperty(String name) {
		return new PropertySet<String>() {

			@Override
			public void serializePart(IOStream stream, String e) {
				stream.putString(e);
			}

			@Override
			public String deserializePart(IOStream stream) {
				return stream.getString();
			}
		};
	}

}
