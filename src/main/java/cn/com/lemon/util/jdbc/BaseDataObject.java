package cn.com.lemon.util.jdbc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import cn.com.lemon.util.jdbc.annotation.Transient;

/**
 * Value对象的基类，当使用<code>DaoTemplate</code>时，Value对象继承这个类，多表关联查询时，
 * 其他表的字段会存入props（Map）中。
 * 
 * @author <a href="mailto:shelloshy@gmail.com">Shaobo Shih</a>
 * @see <code>DaoTemplate</code>
 */
public abstract class BaseDataObject implements Serializable {

	private static final long serialVersionUID = -5075630072275200425L;

	protected Map<String, Object> props;

	public BaseDataObject() {
		props = new HashMap<String, Object>();
	}

	@Transient
	public Map<String, Object> getProps() {
		return props;
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}
}
