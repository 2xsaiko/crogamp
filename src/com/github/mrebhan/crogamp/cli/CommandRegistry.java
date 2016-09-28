package com.github.mrebhan.crogamp.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandRegistry {

	private Map<String, Function<String[], Integer>> commands;
	private Map<String, String> descriptions;

	public CommandRegistry() {
		commands = new HashMap<>();
		descriptions = new HashMap<>();
	}

	public int execute(String[] command) {
		if (command.length > 0) {
			Function<String[], Integer> f = commands.get(command[0]);
			if (f != null) {
				return f.apply(giveMeDaStuff(command));
			}
		}
		return -1;
	}

	public void registerListCommands(String name) {
		registerCommand(name, "Lists all available commands.", this::listCmds);
	}
	
	public void registerCommand(String name, Function<String[], Integer> function) {
		commands.put(name, function);
	}
	
	private void addDescriptionText(String name, String description) {
		descriptions.put(name, description);
	}

	public void registerCommand(String name, String description, Function<String[], Integer> function) {
		registerCommand(name, function);
		addDescriptionText(name, description);
	}

	private int listCmds(String[] in) {
		int i = 2;
		for (String string : commands.keySet()) {
			i = Math.max(i, string.length() + 1);
		}
		ArrayList<String> strings = new ArrayList<>();
		for (String string : commands.keySet()) {
			String out = string;
			while (out.length() < i) {
				out += ' ';
			}
			out += "| ";
			if (descriptions.containsKey(string)) {
				out += descriptions.get(string);
			}
			strings.add(out);
		}
		strings.sort(null);
		strings.forEach(s -> System.out.println(s));
		return 0;
	}
	
	private static String[] giveMeDaStuff(String[] commandline) {
		String[] arguments = new String[commandline.length - 1];
		System.arraycopy(commandline, 1, arguments, 0, arguments.length);
		return arguments;
	}

}
