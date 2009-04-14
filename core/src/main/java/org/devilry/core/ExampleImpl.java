package org.devilry.core;

import javax.ejb.Stateless;

@Stateless(mappedName="Example")
public class ExampleImpl implements ExampleRemote {
	public String test() {
		return "It worked!";
	}
}