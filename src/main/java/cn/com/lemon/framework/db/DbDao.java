package cn.com.lemon.framework.db;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import cn.com.lemon.annotation.Column;
import cn.com.lemon.annotation.ID;

import static cn.com.lemon.base.Preasserts.checkArgument;
import static cn.com.lemon.base.Strings.isNullOrEmpty;
import static cn.com.lemon.framework.db.ResultSets.data;

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
	private AnnotationHelper helper = AnnotationHelper.getInstance();
	private JdbcTemplate jdbcTemplate;

	public <T> List<T> list(String sql, Class<T> requiredType) {
		checkArgument(!isNullOrEmpty(sql));
		List<ResultSet> list = list1(sql);
		if (null != list && list.size() > 0) {
			List<T> result = new ArrayList<T>();
			for (ResultSet resultSet : list) {
				try {
					T t = requiredType.newInstance();
					/* data logic processing */
					if (null != resultSet) {
						singleData(t, resultSet);
						result.add(t);
					}
				} catch (InstantiationException e) {
					LOG.error("[" + requiredType + "]" + "java.lang.Class#newInstance() error!");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					LOG.error("[" + requiredType + "]" + "java.lang.Class#newInstance() error!");
					e.printStackTrace();
				}
			}
		}
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
				result.add(data(rs));
				while (rs.next()) {
					result.add(data(rs));
				}
			}
		});
		return result;
	}

	/**
	 * Generic list data query
	 * 
	 * @param sql
	 *            standard SQL language
	 * @return {@code List}
	 */
	public List<ResultSet> list1(String sql) {
		LOG.debug("[" + sql + "] list!");
		final List<ResultSet> result = new ArrayList<ResultSet>();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				result.add(rs);
				while (rs.next()) {
					result.add(rs);
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
	private <T> T singleData(T t, ResultSet rs) {
		checkArgument(null != t && null != rs);
		try {
			ResultSetMetaData md = rs.getMetaData();
			if (null != md && md.getColumnCount() > 0) {
				Field[] fields = helper.filed(t.getClass());
				if (null != fields && fields.length > 0) {
					List<Field> columnList = new ArrayList<Field>();
					List<Field> idList = new ArrayList<Field>();
					// Handle object attribute annotations
					for (Field field : fields) {
						if (field.isAnnotationPresent(Column.class)) {
							columnList.add(field);
						} else if (field.isAnnotationPresent(ID.class)) {
							idList.add(field);
						}
					}
					// Java object properties are bound to database column data
					if (columnList.size() > 0 || idList.size() > 0) {
						// column list
						if (columnList.size() > 0) {
							for (Field field : columnList) {
								// java bean field name
								String objectFieldName = field.getName();
								// database column name
								String columnName = field.getAnnotation(Column.class).value();
								Object value = ResultSets.data(rs, columnName);
								if (null != value) {
									try {
										helper.setFieldValue(t, objectFieldName, value);
									} catch (IllegalArgumentException e) {
										LOG.error("[" + t.getClass().getName() + "] set [" + objectFieldName
												+ "] error! Becase database table cloumn [" + columnName
												+ "] not exist or error!");
										e.printStackTrace();
									} catch (IllegalAccessException e) {
										LOG.error("[" + t.getClass().getName() + "] set [" + objectFieldName
												+ "] error! Becase database table cloumn [" + columnName
												+ "] not exist or error!");
										e.printStackTrace();
									}
								}
							}
						}
						// id list
						if (idList.size() > 0) {
							for (Field field : idList) {
								// java bean field name
								String objectFieldName = field.getName();
								// database column name
								String columnName = field.getAnnotation(Column.class).value();
								Object value = ResultSets.data(rs, columnName);
								if (null != value) {
									try {
										helper.setFieldValue(t, objectFieldName, value);
									} catch (IllegalArgumentException e) {
										LOG.error("[" + t.getClass().getName() + "] set [" + objectFieldName
												+ "] error! Becase database table cloumn [" + columnName
												+ "] not exist or error!");
										e.printStackTrace();
									} catch (IllegalAccessException e) {
										LOG.error("[" + t.getClass().getName() + "] set [" + objectFieldName
												+ "] error! Becase database table cloumn [" + columnName
												+ "] not exist or error!");
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			LOG.debug("[ResultSet] Get meta data error!");
			e.printStackTrace();
		}
		return t;
	}
}
