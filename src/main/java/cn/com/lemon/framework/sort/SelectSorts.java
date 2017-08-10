package cn.com.lemon.framework.sort;

import static cn.com.lemon.base.Preasserts.checkArgument;

public final class SelectSorts {
	private SelectSorts() {
	}

	public static int[] sort(int[] result) {
		checkArgument(null != result && result.length > 0, "The array that needs to be sorted cannot be empty!");
		int value = 0;
		for (int i = 0; i < result.length; i++) {
			int index = i;
			for (int j = result.length - 1; j > i; j--) {
				if (result[j] < result[index]) {
					index = j;
				}
			}
			value = result[i];
			result[i] = result[index];
			result[index] = value;
		}
		return result;
	}
}
