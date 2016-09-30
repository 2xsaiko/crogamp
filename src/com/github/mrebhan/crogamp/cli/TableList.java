package com.github.mrebhan.crogamp.cli;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class TableList {

	private static final String[] BLINE = { "-", "━" };
	private static final String[] CROSSING = { "-+-", "━╈━" };
	private static final String[] VERTICAL_TSEP = { "|", "│" };
	private static final String[] VERTICAL_BSEP = { "|", "┃" };
	private static final String TLINE = "─";
	private static final String CORNER_TL = "┌";
	private static final String CORNER_TR = "┐";
	private static final String CORNER_BL = "┗";
	private static final String CORNER_BR = "┛";
	private static final String CROSSING_L = "┢";
	private static final String CROSSING_R = "┪";
	private static final String CROSSING_T = "┬";
	private static final String CROSSING_B = "┻";

	private String[] descriptions;
	private ArrayList<String[]> table;
	private int sortBy;
	private int[] tableSizes;
	private int rows;
	private int findex;
	private String filter;
	private boolean ucode;

	public TableList(int rows, String... descriptions) {
		if (descriptions.length != rows) {
			throw new IllegalArgumentException();
		}
		this.sortBy = -1;
		this.filter = null;
		this.rows = rows;
		this.descriptions = descriptions;
		this.table = new ArrayList<>();
		this.tableSizes = new int[rows];
		this.updateSizes(descriptions);
		this.ucode = false;
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

	/**
	 * Adds a row to the table with the specified elements.
	 */

	public TableList addRow(String... elements) {
		if (elements.length != rows) {
			throw new IllegalArgumentException();
		}
		table.add(elements);
		updateSizes(elements);
		return this;
	}

	/**
	 * Sorts the table by the specified column (zero-indexed) Use -1 to turn
	 * off.
	 */

	public TableList sortBy(int par0) {
		this.sortBy = par0;
		return this;
	}

	public TableList filterBy(int par0, String pattern) {
		this.findex = par0;
		this.filter = pattern;
		return this;
	}

	public TableList withUnicode(boolean ucodeEnabled) {
		this.ucode = ucodeEnabled;
		return this;
	}

	public void print() {
		StringBuilder line = null;

		if (ucode) {
			for (int i = 0; i < rows; i++) {
				if (line != null) {
					line.append(CROSSING_T);
				} else {
					line = new StringBuilder();
					line.append(CORNER_TL);
				}
				for (int j = 0; j < tableSizes[i] + 2; j++) {
					line.append(TLINE);
				}
			}
			line.append(CORNER_TR);
			System.out.println(line.toString());

			line = null;
		}

		// print header
		for (int i = 0; i < rows; i++) {
			if (line != null) {
				line.append(" ");
				line.append(gc(VERTICAL_TSEP));
				line.append(" ");
			} else {
				line = new StringBuilder();
				if (ucode) {
					line.append(gc(VERTICAL_TSEP));
					line.append(" ");
				}
			}
			String part = descriptions[i];
			while (part.length() < tableSizes[i]) {
				part += " ";
			}
			line.append(part);
		}
		if (ucode) {
			line.append(" ");
			line.append(gc(VERTICAL_TSEP));
		}
		System.out.println(line.toString());

		// print vertical seperator
		line = null;
		for (int i = 0; i < rows; i++) {
			if (line != null) {
				line.append(gc(CROSSING));
			} else {
				line = new StringBuilder();
				if (ucode) {
					line.append(CROSSING_L);
					line.append(gc(BLINE));
				}
			}
			for (int j = 0; j < tableSizes[i]; j++) {
				line.append(gc(BLINE));
			}
		}
		if (ucode) {
			line.append(gc(BLINE));
			line.append(CROSSING_R);
		}
		System.out.println(line.toString());

		line = null;
		ArrayList<String[]> localTable = table;

		if (filter != null) {
			Pattern p = Pattern.compile(filter);
			localTable.removeIf(arr -> {
				String s = arr[findex];
				return !p.matcher(s).matches();
			});
		}

		if (localTable.isEmpty()) {
			String[] sa = new String[rows];
			localTable.add(sa);
		}

		localTable.forEach(arr -> {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] == null) {
					arr[i] = "";
				}
			}
		});

		if (sortBy > -1) {
			localTable.sort((o1, o2) -> o1[sortBy].compareTo(o2[sortBy]));
		}

		for (String[] strings : localTable) {
			for (int i = 0; i < rows; i++) {
				if (line != null) {
					line.append(" ");
					line.append(gc(VERTICAL_BSEP));
					line.append(" ");
				} else {
					line = new StringBuilder();
					if (ucode) {
						line.append(gc(VERTICAL_BSEP));
						line.append(" ");
					}
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
			if (ucode) {
				line.append(" ");
				line.append(gc(VERTICAL_BSEP));
			}
			System.out.println(line.toString());

			line = null;
		}

		if (ucode) {
			for (int i = 0; i < rows; i++) {
				if (line != null) {
					line.append(CROSSING_B);
				} else {
					line = new StringBuilder();
					line.append(CORNER_BL);
				}
				for (int j = 0; j < tableSizes[i] + 2; j++) {
					line.append(gc(BLINE));
				}
			}
			line.append(CORNER_BR);
			System.out.println(line.toString());
		}

	}

	private String gc(String[] src) {
		return src[ucode ? 1 : 0];
	}

}
