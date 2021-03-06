package jiemi.com.base.fragment;

import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;

import jiemi.com.base.protocl.BasePtl;

/**
 * 类名：Fragment 的抽取父类
 * <p/>
 * 描述：抽取一些常用的属性，方法
 *
 * @author：NIU Date：2016/7/18
 */
public abstract class BaseFragment extends Fragment {

    public Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            processMsg(msg);
        }
    };

    // 联网
    public BasePtl basePtl=new BasePtl(mhandler);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=initView();
        initData();
    }
    /**
     * 加载获取View
     *
     * @return  view View获取的视图;
     */
    public abstract View initView();
    /**
     * 加载获取页面数据
     *
     */
    public abstract void initData();
    /**
     * 解析Message消息
     *
     */
    public abstract void processMsg(Message msg);

}
