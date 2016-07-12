package jiemi.com.base.tools;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringTools {

	/**
	 * 解决GET方式向服务器传�?�数据乱码问�?
	 * 
	 * @param str
	 *            要编码的字符�?
	 * @return 已经编好的字符串
	 */
	// public static boolean isOnResumeBoolean=false;//判断是否是第一次走onresume方法
	// public static boolean localWaiter = false;//判断是否是本地提交订单,true就不弹订单框

	public static String parserUTF8(String str) {
		if (str == null || str.length() <= 0)
			return null;

		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 把阿拉伯数字转换为汉语数字
	 * 
	 * @param str
	 * @return
	 */
	public static String getChinese(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			switch (c) {
			case '0':
				sb.append("零");
				break;
			case '1':
				sb.append("一");
				break;
			case '2':
				sb.append("二");
				break;
			case '3':
				sb.append("三");
				break;
			case '4':
				sb.append("四");
				break;
			case '5':
				sb.append("五");
				break;
			case '6':
				sb.append("六");
				break;
			case '7':
				sb.append("七");
				break;
			case '8':
				sb.append("八");
				break;
			case '9':
				sb.append("九");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * dp2px
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

}
