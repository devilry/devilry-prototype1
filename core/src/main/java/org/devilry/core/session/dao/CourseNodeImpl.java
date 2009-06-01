package org.devilry.core.session.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;

import org.devilry.core.entity.*;

import java.util.List;

@Stateless
public class CourseNodeImpl extends BaseNodeImpl implements CourseNodeRemote {

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(String name, String displayName, long parentId) {
		CourseNode node = new CourseNode();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		node.setParent(getNode(parentId));
		em.persist(node);
		em.flush();
		return node.getId();
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllCourses() {
		Query q = em.createQuery("SELECT c.id FROM CourseNode c");
		return q.getResultList();
	}
}
