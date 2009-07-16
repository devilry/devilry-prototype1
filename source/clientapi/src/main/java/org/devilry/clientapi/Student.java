package org.devilry.clientapi;

import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;

public class Student extends AbstractUser<StudentPeriod> {
	
	Student(long userId, DevilryConnection connection) {
		super(userId, connection);
	}
	
	/*
	 * Does the same as getPeriods
	 * @see org.devilry.clientapi.AbstractUser#getActivePeriods()
	 */
	public List<StudentPeriod> getActivePeriods() throws NamingException, NoSuchObjectException, UnauthorizedException {
		List<StudentPeriod> periods = getPeriods();
		return periods;
	}
		
	class StudentPeriodIterator extends PeriodIterator {
		
		StudentPeriodIterator(List<Long> ids) {
			super(ids);
		}		

		public StudentPeriod next() {
			return new StudentPeriod(deliveryCandidateIterator.next(), connection);
		}
	}
	
	public Iterator<StudentPeriod> periods() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> periodIds =  getPeriodNodeBean().getPeriodsWhereIsStudent();
		return new StudentPeriodIterator(periodIds).iterator();
	}
}
