package com.github.mrebhan.crogamp;

public interface IProgressTracker {

	public void update(int type, double partProgress, double fullProgress, boolean newEntry, Object... texts);
	
}
