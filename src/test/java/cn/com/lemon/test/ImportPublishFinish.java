package cn.com.lemon.test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import cn.com.lemon.base.poi.Excels;
import cn.com.lemon.base.util.Jsons;
import cn.com.lemon.common.connection.Oracles;

public class ImportPublishFinish {

	// private static final String PATH =
	// "https://res.zgshfp.com.cn/upload/djpt/zmfpr/";

	public static void main(String[] args) throws SQLException, IOException {
		ImportPublishFinish.importData();
		// System.out.println("10岁尿毒症女儿：爸爸，我什么时候去上学".length());
		// System.out.println("一辈辛苦，老年让病痛折磨，膀胱癌以做手术".substring(0, 10) + "……");
	}

	public static void importData() throws SQLException, IOException {
		Connection connection = Oracles.newInstance();
		List<String[]> datas = Excels.read(new File("C:\\Users\\Administrator\\Desktop\\11.xlsx"), true);
		String sql = "INSERT INTO shfpsubject.vote_candidate (id,candidate_id,candidate_name,candidate_img,title,url)"
				+ " VALUES(?,?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		int tmp = 1;
		for (String[] data : datas) {
			System.out.println(Jsons.json(data));
			ps.setInt(1, tmp);
			ps.setString(2, data[0].length() == 1 ? "00" + data[0] : data[0].length() == 2 ? "0" + data[0] : data[0]);
			ps.setString(3, data[1].trim());
			ps.setString(4, data[2].trim());
			ps.setString(5, data[3].trim());
			ps.setString(6, data[4].trim());
			ps.executeQuery();
			tmp++;
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
