package jiemi.com.base.tools;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * json解析工具类
 * 
 * @author Zhuo
 * 
 */
public class JsonTools {

	// state:200为成功,404为未找到,403为认证失败,500为内部错误

	public static boolean  stateJson(String json, Activity aty) {
		try {
			JSONObject obj = new JSONObject(json);
			String state = obj.getString("state");
			if (state != null && state.length() > 0) {
			
				if (state.equals("200")) {
					return true;
				}else if(state.equals("404")){
					
				}else if(state.equals("403")){
					
				}else if(state.equals("500")){
					
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
