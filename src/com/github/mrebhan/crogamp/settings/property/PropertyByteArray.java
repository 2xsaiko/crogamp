package com.github.mrebhan.crogamp.settings.property;

import de.marco_rebhan.encodelib.IOStream;

public class PropertyByteArray extends Property<byte[]> {

	@SuppressWarnings("deprecation")
	@Override
	public void serialize(IOStream stream, byte[] t) {
		stream.putInt(t.length);
		stream.putRawByte(t);
	}

	@Override
	public byte[] deserialize(IOStream stream) {
		int length = stream.getInt();
		byte[] stuff = new byte[length];
		for (int i = 0; i < stuff.length; i++) {
			stuff[i] = stream.getByte();
		}
		return stuff;
	}

}
