package cn.com.lemon.base;

import static cn.com.lemon.base.Preassert.checkNotNull;
import static cn.com.lemon.base.Preassert.checkArgument;
import static cn.com.lemon.util.RegularUtil.matchNumeric;

/**
 * Static utility methods pertaining to {@code int} primitives, that are not
 * already found in either {@link Integer} or {@link Arrays}.
 *
 *
 * @author shellpo shih
 * @version 1.0
 */
public final class Ints {
	private Ints() {
	}

	/**
	 * The number of bytes required to represent a primitive {@code int} value.
	 */
	public static final int BYTES = Integer.SIZE / Byte.SIZE;

	/**
	 * The largest power of two that can be represented as an {@code int}.
	 *
	 * @since 10.0
	 */
	public static final int MAX_POWER_OF_TWO = 1 << (Integer.SIZE - 2);

	/**
	 * Returns a hash code for {@code value}; equal to the result of invoking
	 * {@code ((Integer) value).hashCode()}.
	 *
	 * @param value
	 *            a primitive {@code int} value
	 * @return a hash code for the value
	 */
	public static int hashCode(int value) {
		return value;
	}

	/**
	 * Returns {@code true} if {@code target} is present as an element anywhere
	 * in {@code array}.
	 *
	 * @param array
	 *            an array of {@code int} values, possibly empty
	 * @param target
	 *            a primitive {@code int} value
	 * @return {@code true} if {@code array[i] == target} for some value of
	 *         {@code
	 *     i}
	 */
	public static boolean contains(int[] array, int target) {
		for (int value : array) {
			if (value == target) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the index of the first appearance of the value {@code target} in
	 * {@code array}.
	 *
	 * @param array
	 *            an array of {@code int} values, possibly empty
	 * @param target
	 *            a primitive {@code int} value
	 * @return the least index {@code i} for which {@code array[i] == target},
	 *         or {@code -1} if no such index exists.
	 */
	public static int indexOf(int[] array, int target) {
		return indexOf(array, target, 0, array.length);
	}

	// consider making this public
	private static int indexOf(int[] array, int target, int start, int end) {
		for (int i = start; i < end; i++) {
			if (array[i] == target) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the start position of the first occurrence of the specified
	 * {@code
	 * target} within {@code array}, or {@code -1} if there is no such
	 * occurrence.
	 *
	 * <p>
	 * More formally, returns the lowest index {@code i} such that {@code
	 * java.util.Arrays.copyOfRange(array, i, i + target.length)} contains
	 * exactly the same elements as {@code target}.
	 *
	 * @param array
	 *            the array to search for the sequence {@code target}
	 * @param target
	 *            the array to search for as a sub-sequence of {@code array}
	 */
	public static int indexOf(int[] array, int[] target) {
		checkNotNull(array, "array");
		checkNotNull(target, "target");
		if (target.length == 0) {
			return 0;
		}

		outer: for (int i = 0; i < array.length - target.length + 1; i++) {
			for (int j = 0; j < target.length; j++) {
				if (array[i + j] != target[j]) {
					continue outer;
				}
			}
			return i;
		}
		return -1;
	}

	/**
	 * Returns the index of the last appearance of the value {@code target} in
	 * {@code array}.
	 *
	 * @param array
	 *            an array of {@code int} values, possibly empty
	 * @param target
	 *            a primitive {@code int} value
	 * @return the greatest index {@code i} for which {@code array[i] == target}
	 *         , or {@code -1} if no such index exists.
	 */
	public static int lastIndexOf(int[] array, int target) {
		return lastIndexOf(array, target, 0, array.length);
	}

	private static int lastIndexOf(int[] array, int target, int start, int end) {
		for (int i = end - 1; i >= start; i--) {
			if (array[i] == target) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the least value present in {@code array}.
	 *
	 * @param array
	 *            a <i>nonempty</i> array of {@code int} values
	 * @return the value present in {@code array} that is less than or equal to
	 *         every other value in the array
	 * @throws IllegalArgumentException
	 *             if {@code array} is empty
	 */
	public static int min(int... array) {
		checkArgument(array.length > 0);
		int min = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] < min) {
				min = array[i];
			}
		}
		return min;
	}

	/**
	 * Returns the greatest value present in {@code array}.
	 *
	 * @param array
	 *            a <i>nonempty</i> array of {@code int} values
	 * @return the value present in {@code array} that is greater than or equal
	 *         to every other value in the array
	 * @throws IllegalArgumentException
	 *             if {@code array} is empty
	 */
	public static int max(int... array) {
		checkArgument(array.length > 0);
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}

	/**
	 * Returns the values from each provided array combined into a single array.
	 * For example, {@code concat(new int[] {a, b}, new int[] {}, new int[] {c}}
	 * returns the array {@code {a, b, c}}.
	 *
	 * @param arrays
	 *            zero or more {@code int} arrays
	 * @return a single array containing all the values from the source arrays,
	 *         in order
	 */
	public static int[] concat(int[]... arrays) {
		int length = 0;
		for (int[] array : arrays) {
			length += array.length;
		}
		int[] result = new int[length];
		int pos = 0;
		for (int[] array : arrays) {
			System.arraycopy(array, 0, result, pos, array.length);
			pos += array.length;
		}
		return result;
	}

	/**
	 * Returns a string containing the supplied {@code int} values separated by
	 * {@code separator}. For example, {@code join("-", 1, 2, 3)} returns the
	 * string {@code "1-2-3"}.
	 *
	 * @param separator
	 *            the text that should appear between consecutive values in the
	 *            resulting string (but not at the start or end)
	 * @param array
	 *            an array of {@code int} values, possibly empty
	 */
	public static String join(String separator, int... array) {
		checkNotNull(separator);
		if (array.length == 0) {
			return "";
		}

		// For pre-sizing a builder, just get the right order of magnitude
		StringBuilder builder = new StringBuilder(array.length * 5);
		builder.append(array[0]);
		for (int i = 1; i < array.length; i++) {
			builder.append(separator).append(array[i]);
		}
		return builder.toString();
	}

	/**
	 * Returns a array {@code int} values split by {@code separator}. For
	 * example, {@code split("-", 1, 2, 3)} returns the string
	 * {@code "{1,2,3}"}.
	 *
	 * @param separator
	 *            the text that should appear between consecutive values in the
	 *            resulting string (but not at the start or end)
	 * @param array
	 *            an string of {@code int} values, possibly empty
	 */
	public static int[] split(String separator, String array) {
		checkNotNull(separator);
		if (null != array && array.length() == 0) {
			return null;
		}
		int size = array.split(separator).length;
		int[] result = new int[size];
		for (int i = 0; i < size; i++) {
			checkArgument(matchNumeric(array.split(separator)[i]));
			result[i] = Integer.valueOf(array.split(separator)[i]).intValue();
		}
		return result;
	}
}
