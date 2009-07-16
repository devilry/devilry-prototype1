package org.devilry.clientapi;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.clientapi.Student.StudentPeriodIterator;
import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.NodePath;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;


public class AdminCourse extends AbstractCourse<AdminPeriod> {
	
	UserCommon user;
	NodeCommon nodeBean;
	CourseNodeCommon courseNode;
	
	long courseId;
	
	AdminCourse(long courseId, DevilryConnection connection) {
		super(/*courseId, */connection);
		this.courseId = courseId;
	}
		
	protected UserCommon getUserBean() throws NamingException {
		return user == null ? user = connection.getUser() : user;
	}
	
	protected NodeCommon getNodeBean() throws NamingException {
		return nodeBean == null ? nodeBean = connection.getNode() : nodeBean;
	}
	
	protected CourseNodeCommon getCourseBean() throws NamingException {
		return courseNode == null ? courseNode = connection.getCourseNode() : courseNode;
	}
	
	public List<AdminCourse> getCoursesWhereIsAdmin() throws UnauthorizedException, NamingException {
		
		List<Long> nodes = getCourseBean().getCoursesWhereIsAdmin();
	
		List<AdminCourse> adminCourses = new LinkedList<AdminCourse>();
		
		for (long id : nodes) {
			adminCourses.add(new AdminCourse(id, connection));
		}
		return adminCourses;
	}
	
	class AdminPeriodIterator extends PeriodIterator {
		
		AdminPeriodIterator(List<Long> ids) {
			super(ids);
		}		

		public AdminPeriod next() {
			return new AdminPeriod(deliveryCandidateIterator.next(), connection);
		}
	}
	
	public Iterator<AdminPeriod> periods() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> periodIds =  getCourseBean().getPeriods(courseId);
		return new AdminPeriodIterator(periodIds).iterator();
	}
	
	
	public AdminPeriod addPeriod(String name, String displayName, Date start, Date end) throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		long periodId = getPeriodNodeBean().create(name, displayName, start, end, courseId);
		return new AdminPeriod(periodId, connection);
	}

	public void removePeriod(AdminPeriod period) throws NoSuchObjectException, NamingException, UnauthorizedException {
		getPeriodNodeBean().remove(period.periodId);
	}

	
	public String getCourseName() throws NoSuchObjectException, NamingException {
		return getCourseBean().getName(courseId);
	}
	
	public String getCourseDisplayName() throws NoSuchObjectException, NamingException {
		return getCourseBean().getDisplayName(courseId);
	}	
	
	public void setCourseName(String newName) throws UnauthorizedException, NoSuchObjectException, NamingException {
		getCourseBean().setName(courseId, newName);
	}

	public void setCourseDisplayName(String newName) throws UnauthorizedException, NoSuchObjectException, NamingException {
		getCourseBean().setDisplayName(courseId, newName);
	}
	
	public void addCourseAdmin(long userId) throws NoSuchObjectException, NoSuchUserException, UnauthorizedException, NamingException {
		getCourseBean().addCourseAdmin(courseId, userId);
	}
	
	public void removeCourseAdmin(long userId) throws NoSuchObjectException, NoSuchUserException, UnauthorizedException, NamingException {
		getCourseBean().addCourseAdmin(courseId, userId);
	}
	
	public NodePath getPath() throws NamingException, NoSuchObjectException, InvalidNameException {
		return getCourseBean().getPath(courseId);
	}
}
