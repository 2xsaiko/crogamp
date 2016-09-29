package com.github.mrebhan.crogamp.settings;

import de.marco_rebhan.encodelib.IOStream;

public interface IDecoder {

	public Settings createSettings(IOStream stream);
	
}
