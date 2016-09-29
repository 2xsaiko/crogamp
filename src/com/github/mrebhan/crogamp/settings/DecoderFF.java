package com.github.mrebhan.crogamp.settings;

import de.marco_rebhan.encodelib.IOStream;

public class DecoderFF implements IDecoder {

	@Override
	public Settings createSettings(IOStream stream) {
		return new Settings();
	}

}
