package org.devilry.core.dao;

public interface NodeLocal extends BaseNodeInterface {
	long create(String name, String displayName);
	long create(String name, String displayName, long parentId);
}
