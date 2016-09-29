package com.github.mrebhan.crogamp.gm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.mrebhan.crogamp.cli.CommandRegistry;
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

	public static void registerCommands(CommandRegistry reg) {
		reg.registerCommand("gl", "Lists all currently added games", GameLibrary::listGames);
		reg.registerCommand("gs", "Selects the specified game.", "<id>", GameLibrary::selectGame);
		reg.registerCommand("ga", "Adds the specified game.", "<id> <description> <path>", GameLibrary::addGame);
	}

	private static int listGames(String[] args) {
		return 0;
	}

	private static int selectGame(String[] args) {
		return 0;
	}

	private static int addGame(String[] args) {
		return 0;
	}

	private GameLibrary() {

	}

}
