package org.devilry.clientapi.student;

import java.util.List;

import javax.naming.NamingException;

import org.devilry.clientapi.DevilryConnection;
import org.devilry.core.daointerfaces.CourseNodeLocal;

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
	
	public List<StudentPeriod> getPeriods() {
		return null;
	}
}
