package com.github.mrebhan.crogamp;

import com.github.mrebhan.crogamp.cli.CrogampCLA;
import com.github.mrebhan.crogamp.cli.CrogampCLI;
import com.github.mrebhan.crogamp.gui.CrogampGUI;

public class Crogamp {

	public static final String VERSION = "0.1.0 PR";

	public static void main(String[] args) {
		ICrogampInterface ica;
		if (args.length > 0 && args[0].length() > 1 && args[0].charAt(0) == '-' && args[0].substring(1).contains("g")) {
			ica = new CrogampGUI();
		} else {
			ica = args.length > 0 ? new CrogampCLA() : new CrogampCLI();
		}

		int status = ica.start(args);
		Runtime.getRuntime().exit(status);
	}

}
