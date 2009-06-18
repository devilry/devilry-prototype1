package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
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
	
	public List<StudentPeriod> getActivePeriods() throws NamingException, NoSuchObjectException, UnauthorizedException {
		List<StudentPeriod> periods = getPeriods();
		return periods;
	}
	
	
	class StudentPeriodIterator implements Iterable<StudentPeriod>, Iterator<StudentPeriod> {

		Iterator<Long> deliveryCandidateIterator;
		
		StudentPeriodIterator(List<Long> ids) {
			deliveryCandidateIterator = ids.iterator();
		}
		
		public Iterator<StudentPeriod> iterator() {
			return this;
		}

		public boolean hasNext() {
			return deliveryCandidateIterator.hasNext();
		}

		public StudentPeriod next() {
			return new StudentPeriod(deliveryCandidateIterator.next(), connection); 
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	
	Iterator<StudentPeriod> periods() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> periodIds =  getPeriodNodeBean().getPeriodsWhereIsStudent();
		return new StudentPeriodIterator(periodIds).iterator();
	}
	
	
	public List<StudentPeriod> getPeriods() throws NamingException, NoSuchObjectException, UnauthorizedException {
				
		LinkedList<StudentPeriod> periodList = new LinkedList<StudentPeriod>();
		Iterator<StudentPeriod> iter = periods();
		
		while (iter.hasNext()) {
			periodList.add(iter.next());
		}
		return periodList;
	}
}
