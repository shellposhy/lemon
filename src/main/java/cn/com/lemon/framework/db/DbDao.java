package cn.com.lemon.framework.db;

import static cn.com.lemon.base.Strings.blob;
import static cn.com.lemon.base.Strings.clob;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

/**
 * Base on Spring JDBC template
 * 
 * <pre>
	 * <bean id="dbDao" class="com.wondersgroup.hsbp.pay.dao.DbDao">
	 *		<property name="dataSource" ref="dataSource" />
 * </pre>
 * 
 * @author shishb
 * @version 1.0
 */
public class DbDao {
	private final Logger LOG = LoggerFactory.getLogger(DbDao.class.getName());
	private JdbcTemplate jdbcTemplate;

	public <T> List<T> list(String sql, Class<T> requiredType) {
		
		return null;
	}

	/**
	 * Generic list data query
	 * 
	 * @param sql
	 *            standard SQL language
	 * @return {@code List}
	 */
	public List<BaseModel> list(String sql) {
		LOG.debug("[" + sql + "] list!");
		final List<BaseModel> result = new ArrayList<BaseModel>();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				result.add(singleData(rs));
				while (rs.next()) {
					result.add(singleData(rs));
				}
			}
		});
		return result;
	}

	/**
	 * General single field value query, including statistics, summation and so
	 * on.
	 * 
	 * @param sql
	 *            standard SQL language
	 * @param <T>
	 *            Common params,{@code String} or {@code Integer} or
	 *            {@code BigDecimal} and so on
	 * @return {@code <T>}
	 */
	@SuppressWarnings("unchecked")
	public <T> T find(String sql) {
		LOG.debug("[" + sql + "] query!");
		return (T) jdbcTemplate.queryForObject(sql, Object.class);
	}

	/**
	 * Generic method based on standard SQL statement updates
	 * 
	 * @param sql
	 *            standard SQL language
	 * @param {@code int} if 1 true,else false
	 */
	public int update(String sql) {
		LOG.debug("[" + sql + "] update!");
		return jdbcTemplate.update(sql);
	}

	/* spring inject {@code DataSource} */
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/* tools */
	private BaseModel singleData(ResultSet rs) {
		try {
			if (null != rs) {
				ResultSetMetaData md = rs.getMetaData();
				if (null != md && md.getColumnCount() > 0) {
					BaseModel model = new BaseModel();
					for (int i = 1; i <= md.getColumnCount(); i++) {
						String label = md.getColumnLabel(i);
						Object value = null;
						switch (md.getColumnType(i)) {
						case Types.INTEGER:
							value = rs.getInt(i);
							break;
						case Types.BIGINT:
							value = rs.getLong(i);
							break;
						case Types.DECIMAL:
						case Types.NUMERIC:
							value = rs.getBigDecimal(i);
							break;
						case Types.TINYINT:
						case Types.SMALLINT:
							value = rs.getShort(i);
							break;
						case Types.LONGVARBINARY:
						case Types.BLOB:
							value = rs.getBlob(i) == null ? null : blob(rs.getBlob(i));
							break;
						case Types.CLOB:
							value = rs.getClob(i) == null ? null : clob(rs.getClob(i));
							break;
						case Types.DATE:
						case Types.TIME:
						case Types.TIMESTAMP:
							value = rs.getDate(i) == null ? null : new Date(rs.getTimestamp(i).getTime());
							break;
						case Types.CHAR:
						case Types.VARCHAR:
						case Types.LONGNVARCHAR:
							value = rs.getString(i);
							break;
						case Types.DOUBLE:
							value = rs.getDouble(i);
							break;
						case Types.FLOAT:
						case Types.REAL:
							value = rs.getFloat(i);
							break;
						case Types.BOOLEAN:
						case Types.BIT:
							value = rs.getBoolean(i);
							break;
						default:
							value = rs.getString(i);
							break;
						}
						model.put(label, value);
					}
					return model;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
