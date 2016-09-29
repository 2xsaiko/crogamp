package com.github.mrebhan.crogamp.cli;

import java.util.ArrayList;

public class TableList {

	private String[] descriptions;
	private ArrayList<String[]> table;
	private boolean sort;
	private int[] tableSizes;
	private int rows;

	public TableList(int rows, boolean sort, String... descriptions) {
		if (descriptions.length != rows) {
			throw new IllegalArgumentException();
		}
		this.rows = rows;
		this.descriptions = descriptions;
		this.table = new ArrayList<>();
		this.sort = sort;
		this.tableSizes = new int[rows];
		this.updateSizes(descriptions);
	}

	private void updateSizes(String[] elements) {
		for (int i = 0; i < tableSizes.length; i++) {
			if (elements[i] != null) {
				int j = tableSizes[i];
				j = Math.max(j, elements[i].length());
				tableSizes[i] = j;
			}
		}
	}

	public void addRow(String... elements) {
		if (elements.length != rows) {
			throw new IllegalArgumentException();
		}
		table.add(elements);
		updateSizes(elements);
	}

	public void print() {
		// print header
		StringBuilder line = null;
		for (int i = 0; i < rows; i++) {
			if (line != null) {
				line.append(" | ");
			} else {
				line = new StringBuilder();
			}
			String part = descriptions[i];
			while (part.length() < tableSizes[i]) {
				part += " ";
			}
			line.append(part);
		}
		System.out.println(line.toString());

		line = null;
		for (int i = 0; i < rows; i++) {
			if (line != null) {
				line.append("-+-");
			} else {
				line = new StringBuilder();
			}
			for (int j = 0; j < tableSizes[i]; j++) {
				line.append('-');
			}
		}
		System.out.println(line.toString());

		line = null;
		ArrayList<String[]> localTable = table;
		if (sort) {
			localTable.sort((o1, o2) -> o1[0].compareTo(o2[0]));
		}
		for (String[] strings : localTable) {
			for (int i = 0; i < rows; i++) {
				if (line != null) {
					line.append(" | ");
				} else {
					line = new StringBuilder();
				}
				String part = strings[i];
				if (part == null) {
					part = "";
				}
				while (part.length() < tableSizes[i]) {
					part += " ";
				}
				line.append(part);
			}
			System.out.println(line.toString());

			line = null;
		}

	}

}
