package com.github.mrebhan.crogamp;

import com.github.mrebhan.crogamp.cli.CrogampCLA;
import com.github.mrebhan.crogamp.cli.CrogampCLI;

public class Crogamp {

	public static final String VERSION = "0.1.0 PR";

	public static void main(String[] args) {
		ICrogampInterface ica = args.length > 0 ? new CrogampCLA() : new CrogampCLI();

		int status = ica.start(args);
		Runtime.getRuntime().exit(status);
	}

}
