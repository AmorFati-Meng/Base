package jiemi.com.base.activity;

import android.app.Activity;
import android.content.Context;
import android.media.MediaRouter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import jiemi.com.base.R;
import jiemi.com.base.tools.ScreenTools;
import jiemi.com.base.widget.LauncherSecondScreen;


public abstract class BaseAty extends AppCompatActivity implements View.OnClickListener {

    public static BaseAty mForegroundActivity = null;
    public static List<Activity> activitieList=new ArrayList<>();

    @ViewInject(R.id.fly_content)
    public FrameLayout mFrame;


    /**
     *
     * 控制显示双屏，true现显示一样，flase显示不一样
     */
    private boolean screenShow=true;

    private boolean mPaused;
    private LauncherSecondScreen mPresentation;

    private MediaRouter mMediaRouter;


    public  Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            processMsg(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_aty);

      /*  Intent intent = new Intent(this,NotifyService.class);
        startService(intent);*/
        ViewUtils.inject(this);

        mFrame= (FrameLayout) findViewById(R.id.fly_content);
        mMediaRouter = (MediaRouter)getSystemService(Context.MEDIA_ROUTER_SERVICE);
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
        if(!screenShow){

            ScreenTools screenTools=new ScreenTools(mPresentation, mPaused, mForegroundActivity, mMediaRouter);
        }
    }
     /**
      * 控制是否是双屏是否显示一样
      * @param  screenShow true 显示一样 false 显示不一样
      */
     public void setScreenShow(boolean screenShow){
         this.screenShow =screenShow;
     }
    public static BaseAty getForegroundActivity() {
        return mForegroundActivity;
    }


    public void setClick(View... views){
        for(View view : views){
            view.setOnClickListener(this);
        }
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
     /**
      * 解析Message消息
      *
      */
    public abstract  void processMsg(Message msg);

    @Override
    public abstract void onClick(View v);
}
