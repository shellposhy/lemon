package cn.com.lemon.framework.db;

import static cn.com.lemon.base.Strings.blob;
import static cn.com.lemon.base.Strings.clob;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.com.lemon.base.Preasserts.checkArgument;
import static cn.com.lemon.base.Strings.isNullOrEmpty;

/**
 * A table of data representing a database result set tools.
 * 
 * @author shellpo shih
 * @version 1.0
 */
public final class ResultSets {
	private final static Logger LOG = LoggerFactory.getLogger(ResultSets.class.getName());

	private ResultSets() {
	}

	/**
	 * Gets the contents of the specified fields in the database table.
	 * 
	 * @param rs
	 *            {@code ResultSet} A table of data representing a database
	 *            result set
	 * @param columnName
	 *            the database table column name
	 * @return {@code Object} the java object
	 */
	public static Object data(ResultSet rs, String columnName) {
		checkArgument(!isNullOrEmpty(columnName) && null != rs);
		try {
			ResultSetMetaData md = rs.getMetaData();
			if (null != md && md.getColumnCount() > 0) {
				for (int i = 1; i <= md.getColumnCount(); i++) {
					// database table column name
					String fieldName = md.getColumnLabel(i);
					if (columnName.toLowerCase().equals(fieldName.toLowerCase())) {
						return value(md.getColumnType(i), i, rs);
					}
				}
			}
		} catch (SQLException e) {
			LOG.debug("Column index  is not valid!");
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * The database table data converted to Java object data
	 * 
	 * @param rs
	 *            {@code ResultSet} A table of data representing a database
	 *            result set
	 * @return {@code BaseModel} key-value type Java object
	 */
	public static BaseModel data(ResultSet rs) {
		checkArgument(null != rs);
		BaseModel model = new BaseModel();
		try {
			ResultSetMetaData md = rs.getMetaData();
			if (null != md && md.getColumnCount() > 0) {
				for (int i = 1; i <= md.getColumnCount(); i++) {
					String label = md.getColumnLabel(i);
					model.put(label, value(md.getColumnType(i), i, rs));
				}
				return model;
			}
		} catch (SQLException e) {
			LOG.debug("Column index  is not valid!");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * The database table data converted to Java object data
	 * 
	 * @param index
	 *            the java database data type
	 * @param resultSetIndex
	 *            data index
	 * @param rs
	 *            {@code ResultSet} A table of data representing a database
	 *            result set
	 * @return {@code Object} the java object
	 */
	public static Object value(int index, int resultSetIndex, ResultSet rs) {
		Object value = null;
		try {
			switch (index) {
			case Types.INTEGER:
				value = rs.getInt(resultSetIndex);
				break;
			case Types.BIGINT:
				value = rs.getLong(resultSetIndex);
				break;
			case Types.DECIMAL:
			case Types.NUMERIC:
				value = rs.getBigDecimal(resultSetIndex);
				break;
			case Types.TINYINT:
			case Types.SMALLINT:
				value = rs.getShort(resultSetIndex);
				break;
			case Types.LONGVARBINARY:
			case Types.BLOB:
				value = rs.getBlob(resultSetIndex) == null ? null : blob(rs.getBlob(resultSetIndex));
				break;
			case Types.CLOB:
				value = rs.getClob(resultSetIndex) == null ? null : clob(rs.getClob(resultSetIndex));
				break;
			case Types.DATE:
			case Types.TIME:
			case Types.TIMESTAMP:
				value = rs.getDate(resultSetIndex) == null ? null : new Date(rs.getTimestamp(resultSetIndex).getTime());
				break;
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGNVARCHAR:
				value = rs.getString(resultSetIndex);
				break;
			case Types.DOUBLE:
				value = rs.getDouble(resultSetIndex);
				break;
			case Types.FLOAT:
			case Types.REAL:
				value = rs.getFloat(resultSetIndex);
				break;
			case Types.BOOLEAN:
			case Types.BIT:
				value = rs.getBoolean(resultSetIndex);
				break;
			default:
				value = rs.getString(resultSetIndex);
				break;
			}
			return value;
		} catch (SQLException e) {
			LOG.debug("Column index  is not valid!");
			e.printStackTrace();
			return null;
		}
	}
}
