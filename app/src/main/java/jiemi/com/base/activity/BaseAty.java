package jiemi.com.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import jiemi.com.base.R;
import jiemi.com.base.service.NotifyService;


public abstract class BaseAty extends AppCompatActivity {

    public static BaseAty mForegroundActivity = null;
    public static List<Activity> activitieList=new ArrayList<>();

    @ViewInject(R.id.fly_content)
    public FrameLayout mFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_aty);

      /*  Intent intent = new Intent(this,NotifyService.class);
        startService(intent);*/

        addActivity(this);

        getView();
        initData();
        initView();
    }
    private  void getView(){
        View view=setView();
        mFrame.addView(view);
    }
    /**
     * 设置需要加载的view
     * @return 需要加载view
     */
    public abstract View setView();
    /**
     * 设置需要加载的页面数据
     *
     */
    public  abstract void initData();
    /**
     * 设置需要加载的页面
     *
     */
    public  abstract void initView();

    @Override
    protected void onResume() {
        super.onResume();
        this.mForegroundActivity = this;
    }

    public static BaseAty getForegroundActivity() {
        return mForegroundActivity;
    }

    /**
     *将此Activity加入到活动Activity队列中
     *
     * @param activity
     * @return
     */
    private static  boolean addActivity(Activity activity){
        boolean flag=activitieList.add(activity);
        return  flag;
    }

    /**
     * 从活动Activity队列中移除该Activity
     *
     * @param activity
     * @return
     */
    public  static  boolean removeActivity(Activity activity){
        boolean flag=activitieList.remove(activity);
        return  flag;
    }
    /**
     * 获得所有未关闭的活动（Activity）。
     */
    public static  List<Activity> getActivities(){

         List<Activity> copyActivityList=new ArrayList<>(activitieList);

        return  copyActivityList;
    }
    public static  void exit(){
        List<Activity> activities=getActivities();
        for(Activity activity: activities){
            if(activity!=null){
                activity.finish();
                Log.i("Activity " , "Activity " + activity.getClass().getName() + " is finished!!!");
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
