package jiemi.com.base.application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

/**
 * 类名：全局变量
 * <p/>
 * 描述：
 *
 * @author：NIU Date：2016/7/11
 */
public class BaseApplication extends Application {
    /**
     * 获取到主线程的上下文
     */
    private static BaseApplication mContext;
    /**
     * 获取到主线程的handler
     */
    private static Handler mMainThreadHandler;
    /**
     * 获取到主线程
     */
    private static Thread mMainThread;

    /**
     * 获取到主线程的轮询器
     */
    private static Looper mMainThreadLooper;

    /**
     * 获取到主线程id
     */
    private static int mMainTheadId;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        this.mContext = this;
        this.mMainThreadHandler = new Handler();
        this.mMainThread = Thread.currentThread();
        this.mMainThreadLooper = getMainLooper();
        this.mMainTheadId = android.os.Process.myTid();

    }

    public static BaseApplication getApplication() {
        return mContext;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    public static int getMainThreadId() {
        return mMainTheadId;
    }
}
