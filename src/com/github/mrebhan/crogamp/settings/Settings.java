package com.github.mrebhan.crogamp.settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import com.github.mrebhan.crogamp.cli.CommandRegistry;
import com.github.mrebhan.crogamp.gm.GameSettings;
import com.github.mrebhan.crogamp.settings.property.Property;
import com.github.mrebhan.crogamp.settings.property.PropertyBoolean;
import com.github.mrebhan.crogamp.settings.property.PropertyContainer;
import com.github.mrebhan.crogamp.settings.property.PropertyMap;
import com.github.mrebhan.crogamp.settings.property.PropertyMulti;

import de.marco_rebhan.encodelib.IOStream;

public class Settings extends PropertyContainer {

	public static final File FILE = new File("settings");
	public static final File BACKUP_FILE = new File("settings_backup");

	public static final Property<Map<String, GameSettings>> GAMES = new PropertyMulti<GameSettings>().withName("games");
	public static final Property<Map<String, String>> GAME_PATH = PropertyMap.createProperty("game_path");
	public static final Property<Boolean> UNICODE = new PropertyBoolean().withName("ucode");

	@Override
	protected void setDefaults() {
		setValue(GAMES, new HashMap<>());
		setValue(GAME_PATH, new HashMap<>());
		setValue(UNICODE, false);
	}

	public void saveSettings() {
		if (FILE.exists()) {
			try {
				Files.copy(FILE.toPath(), BACKUP_FILE.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		IOStream stream = new IOStream(true);
		serialize(stream);
		try (FileOutputStream os = new FileOutputStream(FILE)) {
			os.write(stream.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void registerCommands(CommandRegistry reg) {
		reg.registerCommand("ucode", "Toggles advanced unicode. (Enhanced characters)", "[on|true|yes|off|false|no]",
				this::cmdUcode);
	}

	private int cmdUcode(String[] args) {
		boolean oldMode = getValue(UNICODE);
		if (args.length == 0) {
			System.out.printf("Advanced unicode is %s.%n", oldMode ? "enabled" : "disabled");
			return oldMode ? 1 : 0;
		} else {
			String in = args[0];
			boolean newMode;
			switch (in) {
			case "on":
			case "true":
			case "yes":
				newMode = true;
				break;
			case "off":
			case "false":
			case "no":
				newMode = false;
				break;
			default:
				System.out.printf("Invalid parameter %s.%nSee syntax for valid parameters.%n", in);
				return -2;
			}
			setValue(UNICODE, newMode);
			if (newMode == oldMode) {
				return 1;
			}
		}

		return 0;
	}

}
