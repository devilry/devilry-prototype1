package org.devilry.clientapi;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;


public class Student {

	DevilryConnection connection;
	
	UserCommon student;
	long studentId;
	
	PeriodNodeCommon periodNode;
	
	Student(long studentId, DevilryConnection connection) {
		this.studentId = studentId;
		this.connection = connection;
	}
	
	private UserCommon getStudentBean() throws NamingException {
		return student == null ? student = connection.getUser() : student;
	}
	
	private PeriodNodeCommon getPeriodNodeBean() throws NamingException {
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
