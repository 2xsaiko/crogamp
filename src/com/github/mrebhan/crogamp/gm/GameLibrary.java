package com.github.mrebhan.crogamp.gm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.mrebhan.crogamp.cli.CommandRegistry;
import com.github.mrebhan.crogamp.cli.TableList;
import com.github.mrebhan.crogamp.settings.Settings;

import de.marco_rebhan.encodelib.IOStream;

public class GameLibrary {

	private static Settings settings;

	private static GameSettings currentGame;

	private static boolean indexFiles() {
		File dir = new File(currentGame.getValue(GameSettings.PATH));
		File db = new File(dir, ".crogamp");
		db.mkdirs();
		if (dir.isDirectory()) {
			ModSettings ms = new ModSettings();
			ms.setValue(ModSettings.BASEGAME, true);
			ms.setValue(ModSettings.ID, " <base game> ");
			ms.setValue(ModSettings.PRIO, 0);
			ms.setValue(ModSettings.ENABLED, true);
			Set<File> files = new HashSet<>();
			Set<File> dirs = new HashSet<>();
			recursiveList(dir, files, dirs);
			System.out.printf("%s files, %s directories%nIndexing...%n", files.size(), dirs.size());
			dirs.forEach(f -> ms.getValue(ModSettings.DIRS).add('/' + dir.toURI().relativize(f.toURI()).getPath()));
			// HexBinaryAdapter hba = new HexBinaryAdapter();

			for (File file : files) {
				String p = '/' + dir.toURI().relativize(file.toURI()).getPath();
				try (InputStream is = new FileInputStream(file)) {
					MessageDigest md = MessageDigest.getInstance("SHA-1");
					int n = 0;
					byte[] buffer = new byte[8192];
					while (n != -1) {
						n = is.read(buffer);
						if (n > 0) {
							md.update(buffer, 0, n);
						}
					}
					byte[] sha1 = md.digest();
					// String sha1s = hba.marshal(sha1);
					File targetRes = currentGame.resource(sha1);
					targetRes.getParentFile().mkdirs();
					if (!targetRes.isFile()) {
						Files.copy(file.toPath(), targetRes.toPath());
					}
					Files.delete(file.toPath());
					Files.createLink(file.toPath(), targetRes.toPath());
					ms.getValue(ModSettings.FILES).put(p, sha1);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
					System.out.println("System does not support SHA-1 hashing algorithm. Aborting");
					return false;
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Couldn't open file. Aborting");
					return false;
				}
			}

			currentGame.getValue(GameSettings.MODS).put(ms.getValue(ModSettings.ID), ms);

		} else {
			// this should never happen
			throw new IllegalStateException("Game folder does not exist!");
		}
		return true;
	}

	private static void recursiveList(File dir, Set<File> files, Set<File> dirs) {
		Arrays.asList(dir.listFiles()).forEach(f -> {
			if (f.isFile()) {
				files.add(f);
			} else if (f.isDirectory()) {
				dirs.add(f);
				recursiveList(f, files, dirs);
			}
		});
	}

	public static Settings getSettings() {
		return settings;
	}

	public static GameSettings getSelectedGame() {
		return currentGame;
	}

	public static void registerCommands(CommandRegistry reg) {
		reg.registerCommand("ga", "Adds the specified game.", "<id> <description> <path>", GameLibrary::addGame);
		reg.registerCommand("gl", "Lists all currently added games", "[pattern]", GameLibrary::listGames);
		reg.registerCommand("gs", "Selects the specified game.", "<id>", GameLibrary::selectGame);
		reg.registerCommand("ma", "Adds a mod to the currently active game.", "<id> <file>", GameLibrary::addMod);
		reg.registerCommand("ml", "Lists all mods for the currently active game.", "[pattern]", GameLibrary::listMods);
		reg.registerCommand("mm", "Moves the specified mod to the specified position in the priority list.",
				"<id> <position>", in -> -10);
		reg.registerCommand("me", "Toggles if the selected mod is active.", "<id>", in -> -10);
		reg.registerCommand("mr", "Deletes the specified mod and removes all associated files.", "<id>", in -> -10);
	}

	private static int listGames(String[] args) {
		TableList tl = new TableList(2, "Game ID", "Full Name").sortBy(1)
				.withUnicode(settings.getValue(Settings.UNICODE));
		if (args.length > 0) {
			String s = String.join(" ", args).replace("?", ".?").replace("*", ".*?");
			tl.filterBy(0, s);
		}
		Map<String, GameSettings> l = settings.getValue(Settings.GAMES);
		l.forEach((id, settings) -> tl.addRow(id, settings.getValue(GameSettings.FULL_NAME)));
		tl.print();
		return 0;
	}

	private static int listMods(String[] args) {
		if (a()) {
			TableList tl = new TableList(3, "Position", "Mod ID", "Enabled").sortBy(1)
					.withUnicode(settings.getValue(Settings.UNICODE));
			currentGame.getValue(GameSettings.MODS)
					.forEach((name, ms) -> tl.addRow(Integer.toString(ms.getValue(ModSettings.PRIO)), name,
							ms.getValue(ModSettings.ENABLED) ? "Yes" : "No"));
			tl.print();
			return 0;
		} else {
			return -1;
		}
	}

	private static int selectGame(String[] args) {
		if (args.length == 1) {
			GameSettings gs = settings.getValue(Settings.GAMES).get(args[0]);
			if (gs == null) {
				System.err.printf("Game %s not registered!", args[0]);
				return -3;
			}
			currentGame = gs;
			return 0;
		}
		return -2;
	}

	private static int addGame(String[] args) {
		if (args.length == 3) {
			String id = args[0];
			String desc = args[1];
			String path = args[2];
			File f = new File(path);
			if (!f.isDirectory()) {
				System.err.printf("Game directory %s does not exist! Aborting.", path);
				return -3;
			}
			GameSettings gs = new GameSettings();
			gs.setValue(GameSettings.ID, id);
			gs.setValue(GameSettings.FULL_NAME, desc);
			gs.setValue(GameSettings.PATH, path);
			settings.getValue(Settings.GAMES).put(id, gs);
			currentGame = gs;
			if (!indexFiles()) {
				currentGame = null;
				settings.getValue(Settings.GAMES).remove(id);
				return -4;
			}
			return 0;
		}
		return -2;
	}

	private static int addMod(String[] args) {
		if (a()) {
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * Checks if a game is selected or not.
	 * 
	 * @return true if a game is selected, false otherwise
	 */

	private static boolean a() {
		if (currentGame == null) {
			System.out.println("Please select a game!");
			return false;
		}
		return true;
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

		currentGame = null;
	}

	private GameLibrary() {

	}

}
