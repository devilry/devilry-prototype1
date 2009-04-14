package org.devilry.core;

import javax.ejb.Remote;

@Remote
public interface ExampleRemote {
	public String test();
}