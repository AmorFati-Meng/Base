package jiemi.com.base.widget;





import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.annotation.SuppressLint;
import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.View;

import android.widget.ImageView;


import com.android.volley.Request;

import jiemi.com.base.R;
import jiemi.com.base.network.Network;
import jiemi.com.base.protocl.BasePtl;

@SuppressLint("NewApi")
public class LauncherSecondScreen extends Presentation {


	    private static  final  int PAGER_VIEW=100;
	 
	    private ViewPager pager;
	    private int mCurrentItem = 0;
	    private int mItem;
	    private Runnable mPagerAction;
	    private boolean isFrist = true;
	    private int[] imgResIDs = new int[]
	    	    { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e };
	    private ArrayList<View> items = new ArrayList<View>();


	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			initView();
		}
	};

	@SuppressLint("NewApi")
	public LauncherSecondScreen(Context outerContext, Display display) {
		super(outerContext, display);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.dialog_second_screen_content);
		 pager = (ViewPager) findViewById(R.id.tuijian_pager);
		initAllItems();
		//initView();
	}

	private void getPagetView(){
		BasePtl basePtl=new BasePtl(mHandler);
		Map<String , String> params=new HashMap<String, String>();
		basePtl.requestData(PAGER_VIEW, Request.Method.POST, Network.PAGER_VIEW,params);

	}
	private void initView(){
		 pager.setAdapter(new PagerAdapter()
	        {

	            @Override
	            public Object instantiateItem(View container, int position)
	            {
	                View layout = items.get(position % items.size());
	                pager.addView(layout);
	                return layout;
	            }
	            

	            @Override
	            public void destroyItem(View container, int position, Object object)
	            {
	                View layout = items.get(position % items.size());
	                pager.removeView(layout);
	            }
	            
	            @Override
	            public boolean isViewFromObject(View arg0, Object arg1)
	            {
	                return arg0 == arg1;
	                
	            }
	            
	            @Override
	            public int getCount()
	            {
	                return imgResIDs.length;
	            }
	            
	        });
	        pager.setOnPageChangeListener(new OnPageChangeListener()
	        {
	            
	            @Override
	            public void onPageSelected(final int arg0)
	            {
	                mCurrentItem = arg0 % items.size();
	                pager.setCurrentItem(mCurrentItem);
	            
	            }
	            
	            @Override
	            public void onPageScrolled(int arg0, float arg1, int arg2)
	            {
	                // TODO Auto-generated method stub
	            }
	            
	            @Override
	            public void onPageScrollStateChanged(int arg0)
	            {
	                // TODO Auto-generated method stub
	                
	            }
	        });
	        mPagerAction = new Runnable()
	        {
	            @Override
	            public void run()
	            {
	                
	                if (mItem != 0)
	                {
	                    if (isFrist == true)
	                    {
	                        mCurrentItem = 0;
	                        isFrist = false;
	                    }
	                    else
	                    {
	                        if (mCurrentItem == items.size() - 1)
	                        {
	                            mCurrentItem = 0;
	                        }
	                        else
	                        {
	                            mCurrentItem++;
	                        }
	                    }
	                    
	                    pager.setCurrentItem(mCurrentItem);
	                   
	                    
	                }
	                pager.postDelayed(mPagerAction, 2500);
	            }
	        };
	        pager.postDelayed(mPagerAction, 100);
	}
	private void initAllItems()
    {

        for (int i = 0; i < imgResIDs.length; i++)
        {
            items.add(initPagerItem(imgResIDs[i]));
        }
        mItem = items.size();
    }
	 private View initPagerItem(int resID)
	    {
	        View layout1 = getLayoutInflater().inflate(R.layout.item_advertise, null);
	        ImageView imageView1 = (ImageView) layout1.findViewById(R.id.header_img);
	        imageView1.setImageResource(resID);
	        return layout1;
	    }
}
