package com.github.mrebhan.crogamp.gm;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import com.github.mrebhan.crogamp.settings.property.Property;
import com.github.mrebhan.crogamp.settings.property.PropertyContainer;
import com.github.mrebhan.crogamp.settings.property.PropertyMulti;
import com.github.mrebhan.crogamp.settings.property.PropertyString;

public class GameSettings extends PropertyContainer {

	public static final Property<String> ID = new PropertyString().withName("id");
	public static final Property<String> FULL_NAME = new PropertyString().withName("fn");
	public static final Property<String> PATH = new PropertyString().withName("path");
	public static final Property<Map<String, ModSettings>> MODS = new PropertyMulti<ModSettings>().withName("mods");

	@Override
	protected void setDefaults() {
		setValue(ID, "");
		setValue(FULL_NAME, "");
		setValue(PATH, "");
		setValue(MODS, new HashMap<>());
	}

	public File resource(byte[] sha1) {
		String sha1s = new HexBinaryAdapter().marshal(sha1);
		return new File(getValue(PATH), "/.crogamp/" + sha1s.substring(0, 2) + "/" + sha1s);
	}

	public void rebuildPriorities() {
		
	}

}
