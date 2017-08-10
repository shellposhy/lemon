package cn.com.lemon.framework.sort;

import static cn.com.lemon.base.Preasserts.checkArgument;

public final class QuickSorts {

	private QuickSorts() {
	}

	public static int[] sort(int[] result) {
		checkArgument(null != result && result.length > 0, "The array that needs to be sorted cannot be empty!");
		int low = 0;
		int high = result.length - 1;
		if (low < high) {
			int middle = partition(result, low, high);
			partition(result, low, middle - 1);
			partition(result, middle + 1, high);
		}
		return result;
	}

	private static int partition(int[] result, int low, int high) {
		// first value as the middle value
		int value = result[low];
		while (low < high) {
			while (low < high && result[high] > value) {
				high--;
			}
			// move the value to low when the value is lesser than middle value
			result[low] = result[high];
			while (low < high && result[low] < value) {
				low++;
			}
			// move the value to low when the value is bigger than middle value
			result[high] = result[low];
		}
		result[low] = value;
		return low;
	}
}
