package com.github.mrebhan.crogamp.gm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.mrebhan.crogamp.settings.property.Property;
import com.github.mrebhan.crogamp.settings.property.PropertyBoolean;
import com.github.mrebhan.crogamp.settings.property.PropertyContainer;
import com.github.mrebhan.crogamp.settings.property.PropertyInt;
import com.github.mrebhan.crogamp.settings.property.PropertyMap;
import com.github.mrebhan.crogamp.settings.property.PropertySet;
import com.github.mrebhan.crogamp.settings.property.PropertyString;

public class ModSettings extends PropertyContainer {

	public static final Property<Map<String, byte[]>> FILES = PropertyMap.createPropertyByteArray("f");
	public static final Property<Set<String>> DIRS = PropertySet.createProperty("d");
	public static final Property<Integer> PRIO = new PropertyInt().withName("p");
	public static final Property<String> ID = new PropertyString().withName("i");
	public static final Property<Boolean> BASEGAME = new PropertyBoolean().withName("b");
	public static final Property<Boolean> ENABLED = new PropertyBoolean().withName("e");
	
	@Override
	protected void setDefaults() {
		setValue(FILES, new HashMap<>());
		setValue(DIRS, new HashSet<>());
		setValue(PRIO, 0);
		setValue(ID, "");
		setValue(BASEGAME, false);
		setValue(ENABLED, true);
	}

}
