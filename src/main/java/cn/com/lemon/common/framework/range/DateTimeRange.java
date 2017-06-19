package cn.com.lemon.common.framework.range;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.com.lemon.common.exception.OperationException;
import cn.com.lemon.util.DateTimeUtil;

public class DateTimeRange extends AbstractSimpleRange<Date> {
	public DateTimeRange() {
	}

	public DateTimeRange(Date start, Date end) {
		setStart(start);
		setEnd(end);
	}

	public static DateTimeRange newDateTimeRange(Date start, Date end) {
		return new DateTimeRange(start, end);
	}

	public boolean isInRange(Date obj) {
		if ((obj.getTime() >= ((Date) getStart()).getTime()) && (obj.getTime() <= ((Date) getEnd()).getTime())) {
			return true;
		}
		return false;
	}

	public List<DateTimeRange> getDayList() throws OperationException {
		List<DateTimeRange> rList = new LinkedList<DateTimeRange>();
		try {
			if (((Date) getEnd()).getTime() >= ((Date) getStart()).getTime()) {
				Date cur = DateTimeUtil.getDateOnly((Date) getStart());
				while (!cur.equals(getEnd())) {
					rList.add(new DateTimeRange(cur,
							DateTimeUtil.getDate(DateTimeUtil.getTheNextDay(cur).getTime() - 1L)));
					cur = DateTimeUtil.getTheNextDay(cur);
				}
				rList.add(new DateTimeRange(cur, cur));
			} else {
				Date cur = DateTimeUtil.getDateOnly((Date) getStart());
				while (!cur.equals(getStart())) {
					rList.add(new DateTimeRange(cur,
							DateTimeUtil.getDate(DateTimeUtil.getTheNextDay(cur).getTime() - 1L)));
					cur = DateTimeUtil.getTheNextDay(cur);
				}
				rList.add(new DateTimeRange(cur, cur));
			}
		} catch (Exception e) {
			throw new OperationException(e.getMessage(), e);
		}
		return rList;
	}
}
