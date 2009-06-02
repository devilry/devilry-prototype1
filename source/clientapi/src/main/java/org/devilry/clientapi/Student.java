package org.devilry.clientapi;

import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.PeriodNodeLocal;
import org.devilry.core.daointerfaces.UserLocal;


public class Student {

	DevilryConnection connection;
	
	UserLocal student;
	long studentId;
	
	Student(long studentId, DevilryConnection connection) {
		this.studentId = studentId;
		this.connection = connection;
	}
	
	private UserLocal getStudentBean() throws NamingException {
		return student == null ? student = connection.getUser() : student;
	}
	
	public List getActivePeriods() {
		return null;
	}
	
	public List getPeriods() {
		return null;	
	}
	
	
}
