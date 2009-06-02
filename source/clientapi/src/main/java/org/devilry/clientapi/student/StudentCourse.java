package org.devilry.clientapi.student;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.clientapi.DevilryConnection;
import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.PeriodNodeLocal;

public class StudentCourse {

	DevilryConnection connection;
	
	CourseNodeLocal courseNode;
	long courseId;	
	
	StudentCourse(long courseId, DevilryConnection connection) {
		this.connection = connection;
		this.courseId = courseId;
	}	
	
	private CourseNodeLocal getCourseNodeBean() throws NamingException {
		
		if (courseNode == null)
			courseNode = connection.getCourseNode();
		
		return courseNode;
	}
	
	public List<StudentPeriod> getActivePeriods() {
		return null;
	}
	
	public List<StudentPeriod> getPeriods() throws NamingException {
		
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
