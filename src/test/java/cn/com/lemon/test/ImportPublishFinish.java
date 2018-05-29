package cn.com.lemon.test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import cn.com.lemon.base.poi.Excels;
import cn.com.lemon.common.connection.Oracles;

public class ImportPublishFinish {

	public static void main(String[] args) throws SQLException, IOException {
		ImportPublishFinish.importData();

	}

	public static void importData() throws SQLException, IOException {
		Connection connection = Oracles.newInstance();
		List<String[]> datas = Excels.read(new File("C:\\Users\\Administrator\\Desktop\\11.xlsx"), true);
		String sql = "INSERT INTO my_publish_setnum (ID,DATESTR,SETNUM) VALUES(?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		String dateStr = datas.get(0)[1];
		int tmp = 1;
		for (String[] data : datas) {
			ps.setInt(1, Integer.valueOf(data[0]));
			ps.setString(2, data[1]);
			if (dateStr.equals(data[1])) {
				ps.setString(3, data[1] + "-" + tmp);
				tmp++;
			} else {
				tmp = 1;
				dateStr = data[1];
				ps.setString(3, data[1] + "-" + tmp);
				tmp++;
			}
			ps.executeQuery();
		}
	}
}

class Account {
	private Integer id;
	private Integer status;
	private Integer fail;
	private Date txdate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getFail() {
		return fail;
	}

	public void setFail(Integer fail) {
		this.fail = fail;
	}

	public Date getTxdate() {
		return txdate;
	}

	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}

}
