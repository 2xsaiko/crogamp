package com.github.mrebhan.crogamp.gm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.mrebhan.crogamp.settings.Settings;

import de.marco_rebhan.encodelib.IOStream;

public class GameLibrary {

	private static Settings settings;

	public static Settings getSettings() {
		return settings;
	}

	static {
		IOStream stream = new IOStream(true);
		File sf = Settings.FILE;
		if (sf.exists()) {
			try (FileInputStream is = new FileInputStream(sf)) {
				byte[] buf = new byte[4096];
				int read;
				while ((read = is.read(buf)) > 0) {
					stream.putRawByte(buf, read);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			stream.putInt(0);
		}

		settings = new Settings();
		settings.deserialize(stream);
	}

	private GameLibrary() {

	}

}
