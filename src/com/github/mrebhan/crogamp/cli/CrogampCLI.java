package com.github.mrebhan.crogamp.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.github.mrebhan.crogamp.Crogamp;
import com.github.mrebhan.crogamp.ICrogampInterface;

public class CrogampCLI implements ICrogampInterface {

	private CommandRegistry reg;

	public CrogampCLI() {
		reg = new CommandRegistry();
		reg.registerListCommands("help");
		registerCommands();
	}

	@Override
	public int start(String[] args) {
		System.out.printf("Crogamp %s  Copyright (C) 2016  Marco Rebhan%nThis program comes "
				+ "with ABSOLUTELY NO WARRANTY; for details type `license'.%n", Crogamp.VERSION);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			while (true) {
				System.out.print("> ");
				String[] stuffs = CommandUtil.split(reader.readLine());
				if (stuffs.length > 0) {
					reg.execute(stuffs);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private void registerCommands() {
		reg.registerCommand("license", "Displays licensing info.", this::cmdLicense);
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

}
