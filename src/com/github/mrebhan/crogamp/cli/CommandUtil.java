package com.github.mrebhan.crogamp.cli;

import java.util.ArrayList;

public class CommandUtil {

	public static String[] split(String commandLine) {
		char[] carr = commandLine.toCharArray();
		boolean quote = false;

		String current = "";
		ArrayList<String> list = new ArrayList<String>();

		for (char c : carr) {
			if (c == '"') {
				quote = !quote;
			} else {
				if (c == ' ' & !quote) {
					if (!current.equals("")) {
						list.add(current);
						current = "";
					}
				} else {
					current += c;
				}
			}
		}

		if (!current.equals("")) {
			list.add(current);
			current = "";
		}

		return list.toArray(new String[0]);
	}
	
}
