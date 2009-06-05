package org.devilry.clientapi;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.PeriodNodeLocal;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.devilry.core.daointerfaces.UserLocal;


public class Student {

	DevilryConnection connection;
	
	UserLocal student;
	long studentId;
	
	PeriodNodeLocal periodNode;
	
	Student(long studentId, DevilryConnection connection) {
		this.studentId = studentId;
		this.connection = connection;
	}
	
	private UserLocal getStudentBean() throws NamingException {
		return student == null ? student = connection.getUser() : student;
	}
	
	private PeriodNodeLocal getPeriodNodeBean() throws NamingException {
		return periodNode == null ? periodNode = connection.getPeriodNode() : periodNode;
	}
	
	public List getActivePeriods() throws NamingException {
		
		List<StudentPeriod> periods = getPeriods();
				
		return periods;
	}
	
	public List<StudentPeriod> getPeriods() throws NamingException {
		
		List<Long> periodIds =  getPeriodNodeBean().getPeriodsWhereIsStudent();
		
		List<StudentPeriod> periods = new LinkedList<StudentPeriod>();
		StudentPeriod periodTmp;
		
		for (long id : periodIds) {
			periodTmp = new StudentPeriod(id, connection);
			periods.add(periodTmp);
		}
		return periods;
	}
}
