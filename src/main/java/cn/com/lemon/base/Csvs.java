package cn.com.lemon.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

/**
 * Static utility methods pertaining to {@code CSV} primitives.
 *
 *
 * @author shellpo shih
 * @version 1.0
 */
public final class Csvs {
	// container for holding data
	private static List<String[]> result = new ArrayList<String[]>();
	public static final char COMMA = ',';

	private Csvs() {

	}

	/**
	 * Gets all the data in the CSV file
	 * <p>
	 * Read the first record of data as column headers.
	 * 
	 * @param path
	 *            the {@code File} path
	 * @return {@code List}
	 */

	public static List<String[]> read(String path) {
		return read(path, true);
	}

	public static List<String[]> read(String path, boolean containHeaders) {
		result.clear();
		try {
			CsvReader reader = new CsvReader(path);
			// Read the first record of data as column headers.
			if (containHeaders)
				reader.readHeaders();
			while (reader.readRecord()) {
				result.add(reader.getValues());
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			return null;
		}
		return result;
	}

	/**
	 * Write all the data in the CSV file
	 * <p>
	 * Write the first record of data as column headers.
	 * 
	 * @param output
	 *            the {@code File} path
	 * @param header
	 * @param data
	 */
	public static void write(String output, String[] header, List<String[]> data) {
		try {
			CsvWriter writer = new CsvWriter(output, COMMA, Charset.forName("UTF-8"));
			// Write the first record of data as column headers.
			if (null != header && header.length > 0) {
				writer.writeRecord(header);
			}
			// Write date
			if (null != data && data.size() > 0) {
				for (String[] record : data) {
					writer.writeRecord(record, true);
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void write(OutputStream stream, String[] header, List<String[]> data) {
		try {
			CsvWriter writer = new CsvWriter(stream, COMMA, Charset.forName("UTF-8"));
			// Write the first record of data as column headers.
			if (null != header && header.length > 0) {
				writer.writeRecord(header);
			}
			// Write date
			if (null != data && data.size() > 0) {
				for (String[] record : data) {
					writer.writeRecord(record, true);
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
