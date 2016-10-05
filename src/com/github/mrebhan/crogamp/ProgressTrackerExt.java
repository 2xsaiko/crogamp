package com.github.mrebhan.crogamp;

public class ProgressTrackerExt implements IProgressTracker {

	private int lastType;

	private IProgressTracker logic;

	public ProgressTrackerExt(IProgressTracker tracker) {
		this.logic = tracker;
	}

	@Override
	public void update(int type, double partProgress, double fullProgress, boolean newEntry, Object... texts) {
		lastType = type;
		if (logic != null)
			logic.update(type, partProgress, fullProgress, newEntry, texts);
	}

	public int lastType() {
		return this.lastType;
	}

}
