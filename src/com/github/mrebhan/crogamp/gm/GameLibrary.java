package com.github.mrebhan.crogamp.gm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.mrebhan.crogamp.settings.IDecoder;

import de.marco_rebhan.encodelib.IOStream;

public class GameLibrary {

	private GameLibrary() {

	}

	static {
		IOStream stream = new IOStream();
		File sf = new File("settings");
		if (sf.exists()) {
			try (FileInputStream is = new FileInputStream(sf)) {
				byte[] buf;
				while (is.available() > 0) {
					buf = new byte[Math.min(4096, is.available())];
					is.read(buf);
					stream.putRawByte(buf);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			stream.putByte((byte) 0xFF);
		}

		byte configVer = stream.getByte();
		String className = IDecoder.class.getPackage().getName() + ".Decoder";
		String id = Integer.toHexString(Byte.toUnsignedInt(configVer)).toUpperCase();
		while (id.length() < 2) {
			id = "0" + id;
		}
		className += id;
		Class<?> c;
		try {
			c = IDecoder.class.getClassLoader().loadClass(className);
			// initialize decoder&load stuff
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.printf("Couldn't load decoder for config file version %s", id);
		}
	}

}
