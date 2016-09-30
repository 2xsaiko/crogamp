package com.github.mrebhan.crogamp.gm;

import java.util.HashMap;
import java.util.Map;

import com.github.mrebhan.crogamp.settings.property.Property;
import com.github.mrebhan.crogamp.settings.property.PropertyContainer;
import com.github.mrebhan.crogamp.settings.property.PropertyMulti;
import com.github.mrebhan.crogamp.settings.property.PropertyString;

public class GameSettings extends PropertyContainer {

	public static final Property<String> ID = new PropertyString().withName("id");
	public static final Property<String> FULL_NAME = new PropertyString().withName("fn");
	public static final Property<String> PATH = new PropertyString().withName("path");
	public static final Property<Map<String, ModSettings>> MOD_FILES = new PropertyMulti<ModSettings>().withName("mods");
	
	@Override
	protected void setDefaults() {
		setValue(ID, "");
		setValue(FULL_NAME, ""); 
		setValue(PATH, "");
		setValue(MOD_FILES, new HashMap<>());
	}

}
