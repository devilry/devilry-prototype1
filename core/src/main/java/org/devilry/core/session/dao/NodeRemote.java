package org.devilry.core.session.dao;

import javax.ejb.*;

@Remote
public interface NodeRemote extends AbstractNodeRemote {
	long create(String name, String displayName);
	long create(String name, String displayName, long parentId);
}