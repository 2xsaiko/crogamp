package com.github.mrebhan.crogamp.settings.property;

import de.marco_rebhan.encodelib.IOStream;

public class PropertyInt extends Property<Integer> {

	@Override
	public void serialize(IOStream stream, Integer t) {
		stream.putInt(t);
	}

	@Override
	public Integer deserialize(IOStream stream) {
		return stream.getInt();
	}
	
}
