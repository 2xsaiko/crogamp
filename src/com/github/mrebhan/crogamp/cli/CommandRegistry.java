package com.github.mrebhan.crogamp.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.github.mrebhan.crogamp.gm.GameLibrary;
import com.github.mrebhan.crogamp.settings.Settings;

public class CommandRegistry {

	private Map<String, Function<String[], Integer>> commands;
	private Map<String, String> descriptions;
	private Map<String, String> syntax;

	public CommandRegistry() {
		commands = new HashMap<>();
		descriptions = new HashMap<>();
		syntax = new HashMap<>();
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
		registerCommand(name, "Lists all available commands.", "[pattern]", this::listCmds);
	}

	public void registerCommand(String name, Function<String[], Integer> function) {
		this.commands.put(name, function);
	}

	private void addDescriptionText(String name, String description) {
		this.descriptions.put(name, description);
	}

	public void registerCommand(String name, String description, Function<String[], Integer> function) {
		registerCommand(name, function);
		addDescriptionText(name, description);
	}

	private void addSyntaxText(String name, String syntax) {
		this.syntax.put(name, syntax);
	}

	public void registerCommand(String name, String description, String syntax, Function<String[], Integer> function) {
		registerCommand(name, description, function);
		addSyntaxText(name, syntax);
	}

	private int listCmds(String[] in) {
		TableList tl = new TableList(3, "Command", "Description", "Syntax").sortBy(0).withUnicode(GameLibrary.getSettings().getValue(Settings.UNICODE));
		if (in.length > 0) {
			String s = String.join(" ", in).replace("?", ".?").replace("*", ".*?");
			tl.filterBy(0, s);
		}
		commands.keySet().forEach(c -> tl.addRow(c, descriptions.get(c), syntax.get(c)));
		tl.print();
		return 0;
	}

	private static String[] giveMeDaStuff(String[] commandline) {
		String[] arguments = new String[commandline.length - 1];
		System.arraycopy(commandline, 1, arguments, 0, arguments.length);
		return arguments;
	}

}
