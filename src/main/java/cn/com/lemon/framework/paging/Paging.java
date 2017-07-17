package cn.com.lemon.framework.paging;

import java.io.Serializable;

/**
 * A <tt>Paging</tt> is a paging basic bean. This class implements
 * {@code Serializable}.
 * 
 * @author shellpo shih
 * @version 1.0
 */
public class Paging implements Serializable {
	private static final long serialVersionUID = 1L;
	// page size
	protected int size;
	// page all size
	protected int total;
	// page number
	protected int count;

	// current page number
	protected int cur;
	// previous page
	protected int pre;
	// next page
	protected int next;
	// loop start
	protected int start;
	// loop end
	protected int end;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCur() {
		return cur;
	}

	public void setCur(int cur) {
		this.cur = cur;
	}

	public int getPre() {
		return pre;
	}

	public void setPre(int pre) {
		this.pre = pre;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
