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
		node.setParent(getNode(Node.class, parentId));
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
		return getNode(CourseNode.class, courseId);
	}

	public boolean exists(long courseId) {
		try {
			return getCourseNode(courseId) != null;
		} catch(ClassCastException e) {
			return false;
		}
	}

	public long getParent(long courseId) {
		return getCourseNode(courseId).getParent().getId();
	}

	public long getIdFromPath(String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getPath(long nodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Long> getCoursesWhereIsAdmin() {
		return getNodesWhereIsAdmin(CourseNode.class);
	}

	public void remove(long nodeId) {
		// TODO Auto-generated method stub		
	}
}
