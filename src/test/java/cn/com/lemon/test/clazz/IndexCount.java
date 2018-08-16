package cn.com.lemon.test.clazz;

import java.io.Serializable;

import cn.com.lemon.annotation.Column;
import cn.com.lemon.annotation.Table;

/**
 * 新官网数据统计类
 * 
 * @author shishb
 * @version 1.0
 */
@Table("grand_year")
public class IndexCount implements Serializable {
	private static final long serialVersionUID = -3151001884894404298L;
	@Column("user_a")
	private Integer loverNumber = 0;
	@Column("user_b")
	private Integer adminNumber = 0;
	@Column("user_c")
	private Integer povertyNumber = 0;
	@Column("pub_a")
	private Integer goodsNumber = 0;
	@Column("pub_b")
	private Integer moneyNumber = 0;

	public static void main(String[] args) {
		System.out.println(IndexCount.class.getClass().getDeclaredAnnotations().length);
	}

	public Integer getLoverNumber() {
		return loverNumber;
	}

	public void setLoverNumber(Integer loverNumber) {
		this.loverNumber = loverNumber;
	}

	public Integer getAdminNumber() {
		return adminNumber;
	}

	public void setAdminNumber(Integer adminNumber) {
		this.adminNumber = adminNumber;
	}

	public Integer getPovertyNumber() {
		return povertyNumber;
	}

	public void setPovertyNumber(Integer povertyNumber) {
		this.povertyNumber = povertyNumber;
	}

	public Integer getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(Integer goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public Integer getMoneyNumber() {
		return moneyNumber;
	}

	public void setMoneyNumber(Integer moneyNumber) {
		this.moneyNumber = moneyNumber;
	}

}
