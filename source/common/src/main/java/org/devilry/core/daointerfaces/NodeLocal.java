package org.devilry.core.daointerfaces;

public interface NodeLocal extends BaseNodeInterface {
	long create(String name, String displayName);
	long create(String name, String displayName, long parentId);
}
