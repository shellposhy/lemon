package cn.com.lemon.base;

import cn.com.lemon.framework.paging.Paging;

import static cn.com.lemon.base.Preassert.checkArgument;

/**
 * Static utility methods pertaining to {@code Paging} primitives.
 *
 * @author shellpo shih
 * @version 1.0
 */
public class Pagings {
	private static final Paging paging = new Paging();

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
		if (total > 0) {
			paging.setCount(total % size == 0 ? total / size : total / size + 1);
			paging.setPre(cur > 1 && paging.getCount() > 1 ? cur - 1 : 1);
			paging.setNext(cur < paging.getCount() ? cur + 1 : paging.getCount());
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
