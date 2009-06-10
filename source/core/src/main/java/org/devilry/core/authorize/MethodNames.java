package org.devilry.core.authorize;

import java.util.HashSet;

class MethodNames extends HashSet<String> {
	public MethodNames(String... methodNames) {
		for (String n : methodNames)
			add(n);
	}

	public String toString() {
		String s = "";
		String sep = "";
		for (String name : this) {
			s += sep + name;
			sep = ",";
		}
		return s;
	}

}