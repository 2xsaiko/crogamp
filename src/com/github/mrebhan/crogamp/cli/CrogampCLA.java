package com.github.mrebhan.crogamp.cli;

import java.util.Stack;

import com.github.mrebhan.crogamp.gm.GameLibrary;
import com.github.mrebhan.crogamp.gm.GameSettings;

public class CrogampCLA extends CrogampCLI {

	@Override
	public int start(String[] args) {
		String clString = new StringBuffer(String.join(" ", args)).reverse().toString();
		int i = 0;
		int j;
		Stack<String> stack = new Stack<>();
		while ((j = clString.indexOf('+')) > 0) {
			String cl = new StringBuffer(clString.substring(0, j)).reverse().toString();
			clString = clString.substring(j + 1).trim();
			stack.push(cl);
		}
		while (!stack.isEmpty() && isRunning() && i == 0) {
			String cl = stack.pop();
			String sc = Integer.toHexString(i & 0xFF);
			sc = (sc.length() < 2 ? "0" + sc : sc).toUpperCase();
			String csg = GameLibrary.getSelectedGame() == null ? "none"
					: GameLibrary.getSelectedGame().getValue(GameSettings.ID);
			System.out.printf("[%s] %s > %s%n", csg, sc, cl);
			String[] stuffs = CommandUtil.split(cl);
			if (stuffs.length > 0) {
				i = reg.execute(stuffs);
			}
		}
		if (i != 0) {
			String sc = Integer.toHexString(i & 0xFF).toUpperCase();
			System.out.printf("Hit error code %s, aborting.%n", sc);
			return i;
		}
		if (isRunning()) {
			System.out.println();
			return super.start(args);
		}
		return 0;
	}

}
