package jiemi.com.base.protocl;

import android.os.Handler;

import java.util.Map;

import jiemi.com.base.application.BaseApplication;
import jiemi.com.base.interfaces.ReqInterface;
import jiemi.com.base.tools.JiemiNetRunner;
import jiemi.com.base.tools.NetUtils;
import jiemi.com.base.tools.UIUtils;

/**
 * 类名：
 * <p/>
 * 描述：
 *
 * @author：NIU Date：2016/7/12
 */
public abstract class BasePtl implements ReqInterface {
    public Handler handler;


    /**
     * 请求数据
     *
     * @param tag    标志�?
     * @param method 请求数据的方�? post/get
     * @param url    请求数据的URL
     * @param params 请求�?要的参数 可以为空
     */
    public void requestData(final int tag, int method, String url,
                            Map<String, String> params) {
        if (NetUtils.isConnected(BaseApplication.getApplication())) {
            JiemiNetRunner.getData(tag, BaseApplication.getApplication(), method, url, params, this);
        } else {
           
            UIUtils.showToastSafe("请检查网络");
        }
    }

    @Override
    public void onError(String error) {
        UIUtils.showToastSafe("请检查网络");
    }

    @Override
    public void onComplete(int tag, String json) {

    }
}
