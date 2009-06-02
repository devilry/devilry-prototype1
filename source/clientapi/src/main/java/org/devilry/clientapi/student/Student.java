package org.devilry.clientapi.student;

import java.util.List;

import org.devilry.clientapi.DevilryConnection;

public class Student {

	DevilryConnection connection;
	
	Student(DevilryConnection connection) {
		this.connection = connection;
	}
	
	public List getActiveCourses() {
		return null;
	}
	
	public List getCourses() {
		return null;	
	}
	
	
}
