package org.devilry.core.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;

import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.entity.*;

import java.util.List;

@Stateless
public class CourseNodeImpl extends BaseNodeImpl implements CourseNodeRemote, CourseNodeLocal {

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

	public List<Long> getPeriods(long courseId) {
		Query q = em.createQuery("SELECT p.id FROM PeriodNode p "
				+ "WHERE p.parent.id = :id");
		q.setParameter("id", courseId);
		return q.getResultList();
	}
	
	private CourseNode getCourseNode(long courseId) {
		return (CourseNode) getNode(courseId);
	}

	public boolean exists(long nodeId) {
		try {
			return getCourseNode(nodeId) != null;
		} catch(ClassCastException e) {
			return false;
		}
	}
}
