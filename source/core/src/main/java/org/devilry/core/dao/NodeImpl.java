package org.devilry.core.dao;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;

import org.devilry.core.daointerfaces.NodeLocal;
import org.devilry.core.daointerfaces.NodeRemote;
import org.devilry.core.entity.Node;

@Stateless
@Interceptors({AuthorizationInterceptor.class})
public class NodeImpl extends BaseNodeImpl implements NodeRemote, NodeLocal {

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(String name, String displayName) {
		Node node = new Node();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		em.persist(node);
		em.flush();
		return node.getId();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(String name, String displayName, long parentId) {
		Node node = new Node();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		node.setParent(getNode(parentId));
		em.persist(node);
		em.flush();
		return node.getId();
	}
}