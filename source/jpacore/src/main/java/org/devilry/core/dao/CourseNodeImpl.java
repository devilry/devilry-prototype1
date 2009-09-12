package org.devilry.core.dao;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.authorize.AuthorizeCourseNode;
import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.NodeLocal;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeLocal;
import org.devilry.core.entity.*;

import java.util.LinkedList;
import java.util.List;

@Stateless
@Interceptors( { AuthorizeCourseNode.class } )
public class CourseNodeImpl extends BaseNodeImpl
		implements CourseNodeRemote, CourseNodeLocal {
	
	@EJB(beanInterface=PeriodNodeLocal.class) 
	private PeriodNodeCommon periodBean;

	@EJB(beanInterface=NodeLocal.class) 
	private NodeCommon nodeBean;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public long create(String name, String displayName, long parentId) 
		throws UnauthorizedException {
		CourseNode node = new CourseNode();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		node.setParent(getNode(Node.class, parentId));
		em.persist(node);
		em.flush();
		return node.getId();
	}

	// No authorization required
	public List<Long> getPeriods(long courseNodeId) {
		Query q = em.createQuery("SELECT p.id FROM PeriodNode p " +
				"WHERE p.course.id = :id");
		q.setParameter("id", courseNodeId);
		return q.getResultList();
	}
	
	// No authorization required
	private CourseNode getCourseNode(long courseNodeId) throws UnauthorizedException {
		return getNode(CourseNode.class, courseNodeId);
	}

	// No authorization required
	public boolean exists(long courseId) throws UnauthorizedException {
		try {
			return getCourseNode(courseId) != null;
		} catch(ClassCastException e) {
			return false;
		}
	}

	// No authorization required
	public long getParentNode(long courseNodeId) throws UnauthorizedException {
		return getCourseNode(courseNodeId).getParent().getId();
	}

	// No authorization required
	public List<Long> getCoursesWhereIsAdmin() throws UnauthorizedException {
		return getNodesWhereIsAdmin(CourseNode.class);
	}

	public boolean isCourseAdmin(long courseNodeId) throws
			NoSuchObjectException, UnauthorizedException {
		CourseNode courseNode = getCourseNode(courseNodeId);
		if(isAdmin(courseNode, userBean.getAuthenticatedUser())) {
			return true;
		} else {
			return nodeBean.isNodeAdmin(getParentNode(courseNodeId));
		}
	}

	public void addCourseAdmin(long courseNodeId, long userId) throws UnauthorizedException {
		CourseNode node = getCourseNode(courseNodeId);
		addAdmin(node, userId);
	}

	public void removeCourseAdmin(long courseNodeId, long userId) throws UnauthorizedException {
		CourseNode node = getCourseNode(courseNodeId);
		removeAdmin(node, userId);
	}
	
	public List<Long> getCourseAdmins(long courseNodeId) throws UnauthorizedException {
		CourseNode node = getCourseNode(courseNodeId);
		return getAdmins(node);
	}
	
	public void remove(long courseNodeId) throws NoSuchObjectException,
			UnauthorizedException {
		// Remove childnodes
		List<Long> childPeriods = getPeriods(courseNodeId);
		for (Long childPeriodId : childPeriods) {
			periodBean.remove(childPeriodId);
		}

		// Remove *this* node
		removeNode(courseNodeId, CourseNode.class);
	}
	
	// No authorization required
	public NodePath getPath(long courseNodeId) throws NoSuchObjectException, InvalidNameException, UnauthorizedException {
		
		CourseNode course = getCourseNode(courseNodeId);
		String courseName = course.getName();
		
		// Get path from parent node
		NodePath path = nodeBean.getPath(course.getParent().getId());
			
		// Add current node name to path
		path.addToEnd(courseName);
				
		return path;
	}
	
	// No authorization required
	public long getIdFromPath(NodePath nodePath) throws NoSuchObjectException, UnauthorizedException {
		
		NodePath pathCopy = new NodePath(nodePath);
		String courseName = pathCopy.removeLastPathElement();
		
		long parentNodeId = nodeBean.getIdFromPath(pathCopy);
		long courseId = getCourseNodeId(courseName, parentNodeId);
		
		return courseId;
	}

	
	// No authorization required
	/**
	 * Get the id of the course node name with parent parentId
	 * @param name
	 * @param parentId
	 * @return
	 */
	protected long getCourseNodeId(String name, long parentId) {
		Query q;

		if (parentId == -1) {
			return -1;
		} 
		
		q = em.createQuery(
				"SELECT n FROM CourseNode n WHERE n.name=:name AND " +
				"n.parent IS NOT NULL AND n.parent.id=:parentId");
		q.setParameter("name", name);
		q.setParameter("parentId", parentId);

		CourseNode node;

		try {
			node = (CourseNode) q.getSingleResult();
		} catch (NoResultException e) {
			node = null;
		}

		return node == null ? -1 : node.getId();
	}
}
