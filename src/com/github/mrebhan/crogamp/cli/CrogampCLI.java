package com.github.mrebhan.crogamp.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.github.mrebhan.crogamp.Crogamp;
import com.github.mrebhan.crogamp.ICrogampInterface;

public class CrogampCLI implements ICrogampInterface {

	@Override
	public int start(String[] args) {
		System.out.printf("Welcome to Crogamp %s!%nType `LICENSE' for license info.%n", Crogamp.VERSION);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			while (true) {
				System.out.print("> ");
				String[] stuffs = CommandUtil.split(reader.readLine());
				if (stuffs.length > 0) {
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
