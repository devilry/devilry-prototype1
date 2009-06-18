package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;


public class Examiner {

	DevilryConnection connection;
	
	Examiner(DevilryConnection connection) {
		this.connection = connection;
	}
		
	UserCommon user;
	long userId;
	
	PeriodNodeCommon periodNode;
	
	Examiner(long userId, DevilryConnection connection) {
		this.userId = userId;
		this.connection = connection;
	}
	
	private UserCommon getUserBean() throws NamingException {
		return user == null ? user = connection.getUser() : user;
	}
	
	private PeriodNodeCommon getPeriodNodeBean() throws NamingException {
		return periodNode == null ? periodNode = connection.getPeriodNode() : periodNode;
	}
	
	class ExaminerPeriodIterator implements Iterable<ExaminerPeriod>, Iterator<ExaminerPeriod> {

		Iterator<Long> periodIterator;
		
		ExaminerPeriodIterator(List<Long> ids) {
			periodIterator = ids.iterator();
		}
		
		public Iterator<ExaminerPeriod> iterator() {
			return this;
		}

		public boolean hasNext() {
			return periodIterator.hasNext();
		}

		public ExaminerPeriod next() {
			return new ExaminerPeriod(periodIterator.next(), connection); 
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	
	Iterator<ExaminerPeriod> periods() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> periodIds =  getPeriodNodeBean().getPeriodsWhereIsExaminer();
		return new ExaminerPeriodIterator(periodIds).iterator();
	}
	
	
	public List<ExaminerPeriod> getPeriods() throws NamingException, NoSuchObjectException, UnauthorizedException {
				
		LinkedList<ExaminerPeriod> periodList = new LinkedList<ExaminerPeriod>();
		Iterator<ExaminerPeriod> iter = periods();
		
		while (iter.hasNext()) {
			periodList.add(iter.next());
		}
		return periodList;
	}
}
