package cn.com.lemon.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import cn.com.lemon.base.util.Jsons;

public class Test {

	public static void main(String[] args) throws JSONException {
		JSONObject objec1 = new JSONObject();

		objec1.put("url",
				"https://cms.zgshfp.com.cn/eportal/fileDir/pc_news/resource/cms/2018/08/img_pc_site/2018080210412799066.png");
		System.out.println(objec1.toString());

		MedicalTreatment medicalTreatment = new MedicalTreatment();
		medicalTreatment.setTITLE_IMAGE_URL(
				"https://cms.zgshfp.com.cn/eportal/fileDir/pc_news/resource/cms/2018/08/img_pc_site/2018080210412799066.png");
		medicalTreatment.setENTITY_URL("https://djres.zgshfp.com.cn/appia1120/pages/loveneeds/need_detail_money.html?type=2&id=3392613");
		
		T t=new T();
		t.setRspCode(200);
		t.setRspDesc("成功");
		t.setType(1);
		//List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map=new HashMap<String, Object>();
		
		
		List<MedicalTreatment> list1=new ArrayList<MedicalTreatment>();
		list1.add(medicalTreatment);
		
		map.put("MedicalTreatment", medicalTreatment);
		t.setCmsList(map);
		
		System.out.println(Jsons.json(t));
	}


}
class T {
	private int rspCode;
	private String rspDesc;
	private int type;

	private Map<String, Object> cmsList;

	public int getRspCode() {
		return rspCode;
	}

	public void setRspCode(int rspCode) {
		this.rspCode = rspCode;
	}

	public String getRspDesc() {
		return rspDesc;
	}

	public void setRspDesc(String rspDesc) {
		this.rspDesc = rspDesc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Map<String, Object> getCmsList() {
		return cmsList;
	}

	public void setCmsList(Map<String, Object> cmsList) {
		this.cmsList = cmsList;
	}



}

class MedicalTreatment {
	private String TITLE_IMAGE_URL;
	private String ENTITY_URL;

	public String getTITLE_IMAGE_URL() {
		return TITLE_IMAGE_URL;
	}

	public void setTITLE_IMAGE_URL(String tITLE_IMAGE_URL) {
		TITLE_IMAGE_URL = tITLE_IMAGE_URL;
	}

	public String getENTITY_URL() {
		return ENTITY_URL;
	}

	public void setENTITY_URL(String eNTITY_URL) {
		ENTITY_URL = eNTITY_URL;
	}

}