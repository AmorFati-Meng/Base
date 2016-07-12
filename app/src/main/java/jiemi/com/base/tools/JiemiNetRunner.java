package jiemi.com.base.tools;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;
import java.util.Set;

import jiemi.com.base.interfaces.ReqInterface;

/**
 * 本应用封装的请求数据的runner
 * @author sun_zhen
 * @date  2014-9-15
 */
public class JiemiNetRunner {

	/**
	 * 封装�?个请求数据的方法，调用对应中的接�? 的方�?
	 * 
	 * @param tag
	 *            请求标志
	 * @param context
	 *            上下文环境
	 * @param method
	 *            请求数据方式
	 * @param url
	 *            请求URL
	 * @param params
	 *            请求参数
	 * @param listener
	 *            回调的接口?
	 */
	public static void getData(final int tag, Context context, int method,
			String url, final Map<String, String> params,
			final ReqInterface listener) {
		StringRequest request = null;
		if (Method.GET == method) {
			// get方式请求
			String lastUrl = url;
			Set<String> set = params.keySet();
			if (set.size() > 0)
				for (String s : set) {
					if (!lastUrl.contains("?")) {
						String value = StringTools.parserUTF8(params.get(s));
						lastUrl = lastUrl + "?" + s + "=" + value;
					} else {
						String value = StringTools.parserUTF8(params.get(s));
						lastUrl = lastUrl + "&" + s + "=" + value;
					}
				}
			else {
				lastUrl = url;
			}
			
			Log.d("json", "最终请求地址" + lastUrl);

			request = new StringRequest(Request.Method.GET, lastUrl,
					new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							listener.onComplete(tag, response);
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							listener.onError(error.toString());
						}
					});
		} else if (Method.POST == method) {
			// post请求方式
			request = new StringRequest(method, url,
					new Response.Listener<String>() {

						@Override
						public void onResponse(String response) {
							listener.onComplete(tag, response);
						}

					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							listener.onError(error.toString());
						}

					}) {

				@Override
				protected Map<String, String> getParams()
						throws AuthFailureError {
					return params;
				}

			};
		}
		// 将这个请求添加到队列中
		Volley.newRequestQueue(context).add(request);
	}

}