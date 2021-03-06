package com.github.mrebhan.crogamp.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.github.mrebhan.crogamp.Crogamp;
import com.github.mrebhan.crogamp.ICrogampInterface;
import com.github.mrebhan.crogamp.gm.GameLibrary;
import com.github.mrebhan.crogamp.gm.GameSettings;

public class CrogampCLI implements ICrogampInterface {

	protected final CommandRegistry reg;
	private boolean running;

	public CrogampCLI() {
		reg = new CommandRegistry();
		reg.registerListCommands("help");
		registerCommands();
		running = true;
	}

	@Override
	public int start(String[] args) {
		System.out.printf("Crogamp %s  Copyright (C) 2016  Marco Rebhan%nThis program comes "
				+ "with ABSOLUTELY NO WARRANTY; for details type `license'"
				+ ".%nType `help' for a list of commands.%nType `bye' to exit.%n%n", Crogamp.VERSION);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			int lastSC = 0;
			while (running) {
				String sc = Integer.toHexString(lastSC & 0xFF);
				sc = (sc.length() < 2 ? "0" + sc : sc).toUpperCase();
				String csg = GameLibrary.getSelectedGame() == null ? "none"
						: GameLibrary.getSelectedGame().getValue(GameSettings.ID);
				System.out.printf("[%s] %s > ", csg, sc);
				String[] stuffs = CommandUtil.split(reader.readLine());
				if (stuffs.length > 0) {
					lastSC = reg.execute(stuffs);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private void registerCommands() {
		reg.registerCommand("license", "Displays licensing info.", this::cmdLicense);
		reg.registerCommand("bye", "Quits the application.", this::cmdExit);
		GameLibrary.registerCommands(reg);
		GameLibrary.getSettings().registerCommands(reg);
	}

	private int cmdLicense(String[] args) {
		System.out.printf("This program is free software; you can redistribute it and/or "
				+ "modify%nit under the terms of the GNU General Public License"
				+ " as published by%nthe Free Software Foundation; either versi"
				+ "on 3 of the License, or%n(at your option) any later version."
				+ "%n%nThis program is distributed in the hope that it will be "
				+ "useful,%nbut WITHOUT ANY WARRANTY; without even the implied "
				+ "warranty of%nMERCHANTABILITY or FITNESS FOR A PARTICULAR PUR"
				+ "POSE.  See the%nGNU General Public License for more details."
				+ "%n%nYou should have received a copy of the GNU General Publi"
				+ "c License%nalong with this program. If not, see http://www.gnu.org/licenses/.%n");
		return 0;
	}

	private int cmdExit(String[] args) {
		running = false;
		GameLibrary.getSettings().saveSettings();
		System.out.println("Goodbye!");
		return 0;
	}

	public boolean isRunning() {
		return running;
	}

}
