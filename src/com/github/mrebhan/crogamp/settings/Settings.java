package com.github.mrebhan.crogamp.settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.mrebhan.crogamp.settings.property.Property;
import com.github.mrebhan.crogamp.settings.property.PropertyContainer;
import com.github.mrebhan.crogamp.settings.property.PropertyLong;
import com.github.mrebhan.crogamp.settings.property.PropertyMap;

import de.marco_rebhan.encodelib.IOStream;

public class Settings extends PropertyContainer {

	public static final File FILE = new File("settings");

	public static final Property<Map<String, String>> GAMES = PropertyMap.createProperty("games");
	public static final Property<Long> LAST_START_TIME = PropertyLong.createProperty("lst");

	public Settings() {
		setDefaults();
	}

	@Override
	protected void setDefaults() {
		setValue(GAMES, new HashMap<>());
		setValue(LAST_START_TIME, 0L);
	}

	public void saveSettings() {
		setValue(LAST_START_TIME, System.currentTimeMillis());
		IOStream stream = new IOStream(true);
		serialize(stream);
		try (FileOutputStream os = new FileOutputStream(FILE)) {
			os.write(stream.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
