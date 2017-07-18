package cn.com.lemon.framework.sort;

import static cn.com.lemon.base.Preassert.checkArgument;

public final class BubbleSorts {

	private BubbleSorts() {
	}

	public static int[] sort(int[] result) {
		checkArgument(null != result && result.length > 0, "The array that needs to be sorted cannot be empty!");
		for (int i = 0; i < result.length - 1; i++) {
			for (int j = 0; j < result.length - i - 1; j++) {
				if (result[j] > result[j + 1]) {
					int value = result[j];
					result[j] = result[j + 1];
					result[j + 1] = value;
				}
			}
		}
		return result;
	}
}
