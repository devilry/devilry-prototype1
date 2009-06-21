package org.devilry.clientapi;

import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;


public class Examiner extends AbstractUser<ExaminerPeriod> {
	
	Examiner(long userId, DevilryConnection connection) {
		super(userId, connection);
	}

	class ExaminerPeriodIterator extends PeriodIterator {

		ExaminerPeriodIterator(List<Long> ids) {
			super(ids);
		}		

		public ExaminerPeriod next() {
			return new ExaminerPeriod(deliveryCandidateIterator.next(), connection);
		}
	}

	public Iterator<ExaminerPeriod> periods() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> periodIds =  getPeriodNodeBean().getPeriodsWhereIsExaminer();
		return new ExaminerPeriodIterator(periodIds).iterator();
	}
}
