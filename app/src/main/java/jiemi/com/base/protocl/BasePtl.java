package jiemi.com.base.protocl;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Map;

import jiemi.com.base.application.BaseApplication;
import jiemi.com.base.interfaces.ReqInterface;
import jiemi.com.base.tools.JiemiNetRunner;
import jiemi.com.base.tools.NetUtils;
import jiemi.com.base.tools.UIUtils;

/**
 * 类名：网络请求
 * <p/>
 * 描述：
 *
 * @author：NIU Date：2016/7/12
 */
public  class BasePtl implements ReqInterface {

    public Handler handler;
    public BasePtl(Handler handler){
        this.handler=handler;
    }
    /**
     * 请求数据
     *
     * @param tag    标志
     * @param method 请求数据的方式 post/get
     * @param url    请求数据的URL
     * @param params 请求需要的参数 可以为空
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
        Message msg=Message.obtain();
        Bundle bundle=new Bundle();
        bundle.putString("json",json);
        msg.setData(bundle);
        msg.what=tag;
        handler.sendMessage(msg);
    }

}
