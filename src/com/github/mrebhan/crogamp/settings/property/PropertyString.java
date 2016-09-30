package com.github.mrebhan.crogamp.settings.property;

import de.marco_rebhan.encodelib.IOStream;

public class PropertyString extends Property<String> {

	@Override
	public void serialize(IOStream stream, String t) {
		stream.putString(t);
	}

	@Override
	public String deserialize(IOStream stream) {
		return stream.getString();
	}

}
