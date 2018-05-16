package cn.com.lemon.test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.lemon.base.poi.Excels;
import cn.com.lemon.common.connection.Oracles;

public class ImportPublishFinish {

	public static void main(String[] args) throws SQLException, IOException {
		ImportPublishFinish.importData();

	}

	public static void importData() throws SQLException, IOException {
		Connection connection = Oracles.newInstance();
		List<String[]> datas = Excels.read(new File("C:\\Users\\Administrator\\Desktop\\11.xlsx"), true);
		String sql = "INSERT INTO MY_PUBLISH_STATUS (id,status,fail) VALUES(?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		Map<Integer, Account> result = new HashMap<Integer, Account>();
		int z = 0;
		int s = 0;
		int f = 0;
		int o = 0;
		for (String[] data : datas) {
			if (!result.containsKey(Integer.valueOf(data[0]))) {
				Account account = new Account();
				account.setId(Integer.valueOf(data[0]));
				if (data[1].trim().equals("打款中")) {
					account.setStatus(3);
					z++;
				} else if (data[1].trim().equals("成功")) {
					account.setStatus(4);
					s++;
				} else if (data[1].trim().equals("失败")) {
					account.setStatus(5);
					f++;
				} else {
					account.setStatus(0);
					o++;
				}
				if (data.length == 3) {
					if (data[2].trim().equals("账号原因")) {
						account.setFail(5);
					} else {
						account.setFail(9);
					}
				} else {
					account.setFail(0);
				}
				result.put(Integer.valueOf(data[0]), account);
			}
		}
		System.out.println("total=" + datas.size());
		System.out.println("size=" + result.size());
		int t = z + s + f + o;
		System.out.println("付款中=" + z);
		System.out.println("成功=" + s);
		System.out.println("失败=" + f);
		System.out.println("其他=" + o);
		System.out.println("数据统计=" + t);
		for (Map.Entry<Integer, Account> entry : result.entrySet()) {
			ps.setInt(1, entry.getKey());
			ps.setInt(2, entry.getValue().getStatus());
			ps.setInt(3, entry.getValue().getFail());
			// ps.executeQuery();
		}
	}
}

class Account {
	private Integer id;
	private Integer status;
	private Integer fail;

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

}
