package com.github.mrebhan.crogamp.settings.property;

import de.marco_rebhan.encodelib.IOStream;

public class PropertyBoolean extends Property<Boolean> {

	@Override
	public void serialize(IOStream stream, Boolean t) {
		stream.putBool(t);
	}

	@Override
	public Boolean deserialize(IOStream stream) {
		return stream.getBool();
	}
	
}
