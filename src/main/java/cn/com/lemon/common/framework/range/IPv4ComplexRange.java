package cn.com.lemon.common.framework.range;

import java.net.Inet4Address;
import java.util.HashSet;
import java.util.Set;

public class IPv4ComplexRange {
	private Set<IPv4SimpleRange> positiveSet = new HashSet<IPv4SimpleRange>();
	private Set<IPv4SimpleRange> minusSet = new HashSet<IPv4SimpleRange>();

	public boolean isInRange(Inet4Address obj) {
		boolean inRange = false;
		if (this.positiveSet != null) {
			for (IPv4SimpleRange range : this.positiveSet) {
				if (range.isInRange(obj)) {
					inRange = true;
					break;
				}
			}
		}
		if (this.minusSet != null) {
			for (IPv4SimpleRange range : this.minusSet) {
				if (range.isInRange(obj)) {
					inRange = false;
					break;
				}
			}
		}
		return inRange;
	}

	public Set<IPv4SimpleRange> getPositiveSet() {
		return this.positiveSet;
	}

	public void setPositiveSet(Set<IPv4SimpleRange> positiveSet) {
		this.positiveSet = positiveSet;
	}

	public Set<IPv4SimpleRange> getMinusSet() {
		return this.minusSet;
	}

	public void setMinusSet(Set<IPv4SimpleRange> minusSet) {
		this.minusSet = minusSet;
	}
}
