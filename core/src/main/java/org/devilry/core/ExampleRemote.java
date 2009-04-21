package org.devilry.core;

import javax.ejb.Remote;

@Remote
public interface ExampleRemote {
	public long test();
	public String getData(long id);
}