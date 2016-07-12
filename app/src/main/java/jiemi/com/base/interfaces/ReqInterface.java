package jiemi.com.base.interfaces;

/**
 * 定义接口，用于请求数据的调用
 * @author niu
 * @date 2016-7-12
 */
public interface ReqInterface {
	/**
	 * 请求错误方法
	 * @param error
	 */
	void onError(String error);
	/**
	 * 请求成功完成方法
	 */
	void onComplete(int tag, String json);
}
