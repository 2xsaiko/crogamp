package com.github.mrebhan.crogamp.settings.property;

import de.marco_rebhan.encodelib.IOStream;

public class PropertyLong extends Property<Long> {

	@Override
	public void serialize(IOStream stream, Long t) {
		stream.putLong(t);
	}

	@Override
	public Long deserialize(IOStream stream) {
		return stream.getLong();
	}
	
}
