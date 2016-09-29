package com.github.mrebhan.crogamp.settings.property;

import de.marco_rebhan.encodelib.IOStream;

public abstract class Property<T> {
	
	private String name = null;
	
	public abstract void serialize(IOStream stream, T t);
	
	public abstract T deserialize(IOStream stream);
	
	public Property<T> withName(String name) {
		if (this.name != null) {
			throw new IllegalStateException("Name has already been set!");
		}
		this.name = name;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
}
