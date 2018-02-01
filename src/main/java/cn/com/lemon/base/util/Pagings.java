package cn.com.lemon.base.util;

import cn.com.lemon.framework.paging.Paging;

import static cn.com.lemon.base.Preasserts.checkArgument;

/**
 * Static utility methods pertaining to {@code Paging} primitives.
 *
 * @author shellpo shih
 * @version 1.0
 */
public final class Pagings {
	private static final Paging paging = new Paging();
	private static final int LOOP_SIZE = 5;

	private Pagings() {
	}

	/**
	 * Return {@code Paging},The paging processed.
	 * 
	 * @param size
	 *            the page size number
	 * @param total
	 *            the total number
	 * @param cur
	 *            the current page number
	 * @return {@code Paging}
	 */
	public static Paging page(int size, int total, int cur) {
		checkArgument(size > 0 && cur > 0);
		page(size, total, cur, LOOP_SIZE);
		return paging;
	}

	/**
	 * Return {@code Paging},The paging processed.
	 * 
	 * @param size
	 *            the page size number
	 * @param total
	 *            the total number
	 * @param cur
	 *            the current page number
	 * @param loopSize
	 *            the page loop counter size
	 * @return {@code Paging}
	 */
	public static Paging page(int size, int total, int cur, int loopSize) {
		checkArgument(size > 0 && cur > 0);
		if (total > 0) {
			paging.setCount(total % size == 0 ? total / size : total / size + 1);
			checkArgument(cur <= paging.getCount(),
					"The current page number must be lesser than the count page number!");
			paging.setPre(cur > 1 && paging.getCount() > 1 && cur <= paging.getCount() ? cur - 1 : 1);
			paging.setNext(cur + 1 <= paging.getCount() ? cur + 1 : paging.getCount());
			if (loopSize >= paging.getCount()) {
				paging.setStart(1);
				paging.setEnd(paging.getCount());
			} else {
				paging.setStart(cur <= loopSize ? 1
						: cur % loopSize != 0 ? ((cur / loopSize) * loopSize + 1)
								: ((cur / loopSize) - 1) * loopSize + 1);
				paging.setEnd((paging.getStart() + loopSize - 1) > paging.getCount() ? paging.getCount()
						: paging.getStart() + loopSize - 1);
			}
		} else {// no data
			paging.setCount(0);
			paging.setPre(0);
			paging.setNext(0);
			paging.setStart(0);
			paging.setEnd(0);
		}
		paging.setSize(size);
		paging.setTotal(total);
		paging.setCur(cur);
		return paging;
	}
}
