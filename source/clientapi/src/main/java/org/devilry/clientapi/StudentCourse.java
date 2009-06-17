package org.devilry.clientapi;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.CourseNodeCommon;

public class StudentCourse {

	DevilryConnection connection;
	
	CourseNodeCommon courseNode;
	long courseId;	
	
	StudentCourse(long courseId, DevilryConnection connection) {
		this.connection = connection;
		this.courseId = courseId;
	}	
	
	private CourseNodeCommon getCourseNodeBean() throws NamingException {
		return courseNode == null ? courseNode = connection.getCourseNode() : courseNode;
	}
	
	public List<StudentPeriod> getActivePeriods() {
		return null;
	}
	
	public List<StudentPeriod> getPeriods() throws NamingException, NoSuchObjectException, UnauthorizedException {
		
		List<Long> ids = getCourseNodeBean().getPeriods(courseId);
		
		List<StudentPeriod> studentPeriods = new LinkedList<StudentPeriod>();
		StudentPeriod tmpPeriod;	
		
		for (long id : ids) {
			tmpPeriod = new StudentPeriod(id, connection);
			studentPeriods.add(tmpPeriod);
		}
		
		return studentPeriods;
	}
}
