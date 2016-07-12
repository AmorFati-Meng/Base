package jiemi.com.base.tools;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import jiemi.com.base.activity.BaseAty;
import jiemi.com.base.application.BaseApplication;


public class UIUtils {

	public static Context getContext() {
		return BaseApplication.getApplication();
	}

	public static Thread getMainThread() {
		return BaseApplication.getMainThread();
	}

	public static long getMainThreadId() {
		return BaseApplication.getMainThreadId();
	}

	/** dip转换px */
	public static int dip2px(int dip) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/** pxz转换dip */
	public static int px2dip(int px) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/** 获取主线程的handler */
	public static Handler getHandler() {
		return BaseApplication.getMainThreadHandler();
	}

	/** 延时在主线程执行runnable */
	public static boolean postDelayed(Runnable runnable, long delayMillis) {
		return getHandler().postDelayed(runnable, delayMillis);
	}

	/** 在主线程执行runnable */
	public static boolean post(Runnable runnable) {
		return getHandler().post(runnable);
	}

	/** 从主线程looper里面移除runnable */
	public static void removeCallbacks(Runnable runnable) {
		getHandler().removeCallbacks(runnable);
	}

	public static View inflate(int resId){
		return LayoutInflater.from(getContext()).inflate(resId,null);
	}

	/** 获取资源 */
	public static Resources getResources() {
		return getContext().getResources();
	}

	/** 获取文字 */
	public static String getString(int resId) {
		return getResources().getString(resId);
	}

	/** 获取文字数组 */
	public static String[] getStringArray(int resId) {
		return getResources().getStringArray(resId);
	}

	/** 获取dimen */
	public static int getDimens(int resId) {
		return getResources().getDimensionPixelSize(resId);
	}

	/** 获取drawable */
	public static Drawable getDrawable(int resId) {
		return getResources().getDrawable(resId);
	}

	/** 获取颜色 */
	public static int getColor(int resId) {
		return getResources().getColor(resId);
	}

	/** 获取颜色选择器 */
	public static ColorStateList getColorStateList(int resId) {
		return getResources().getColorStateList(resId);
	}
	//判断当前的线程是不是在主线程 
	public static boolean isRunInMainThread() {
		return android.os.Process.myTid() == getMainThreadId();
	}
    
	public static void runInMainThread(Runnable runnable) {
		if (isRunInMainThread()) {
			runnable.run();
		} else {
			post(runnable);
		}
	}

	public static void startActivity(Intent intent){



		BaseAty activity = BaseAty.getForegroundActivity();
		if(activity != null){
			activity.startActivity(intent);
		}else{
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getContext().startActivity(intent);
		}
	}

	/** 对toast的简易封装。线程安全，可以在非UI线程调用。 */
	public static void showToastSafe(final int resId) {
		showToastSafe(getString(resId));
	}

	/** 对toast的简易封装。线程安全，可以在非UI线程调用。 */
	public static void showToastSafe(final String str) {
		if (isRunInMainThread()) {
			showToast(str);
		} else {
			post(new Runnable() {
				@Override
				public void run() {
					showToast(str);
				}
			});
		}
	}

	private static void showToast(String str) {
		BaseAty frontActivity = BaseAty.getForegroundActivity();
		if (frontActivity != null) {
			Toast.makeText(frontActivity, str, Toast.LENGTH_LONG).show();
			
		}
	}
	/**
	 * 掉此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
	 * 
	 */
	 public String dataOne(String time) {
         SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",Locale.CHINA);
         Date date;
         String times = null;
         try {
                 date = sdr.parse(time);
                 long l = date.getTime();
                 String stf = String.valueOf(l);
                 times = stf.substring(0, 10);
         } catch (ParseException e) {
                 e.printStackTrace();
         }
         return times;
 } 
	 public static Bitmap compressImage(Bitmap image) {  
		  
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
	        int options = 100;  
	        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
	            baos.reset();//重置baos即清空baos  
	            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
	            options -= 10;//每次都减少10  
	        }  
	        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
	        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
	        return bitmap;  
	    }  
	 public static Bitmap comp(Bitmap image) {  
	      
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();         
		    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
		    if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
		        baos.reset();//重置baos即清空baos  
		        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中  
		    }  
		    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
		    BitmapFactory.Options newOpts = new BitmapFactory.Options();  
		    //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
		    newOpts.inJustDecodeBounds = true;  
		    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
		    newOpts.inJustDecodeBounds = false;  
		    int w = newOpts.outWidth;  
		    int h = newOpts.outHeight;  
		    //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
		    float hh = 800f;//这里设置高度为800f  
		    float ww = 480f;//这里设置宽度为480f  
		    //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
		    int be = 1;//be=1表示不缩放  
		    if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
		        be = (int) (newOpts.outWidth / ww);  
		    } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
		        be = (int) (newOpts.outHeight / hh);  
		    }  
		    if (be <= 0)  
		        be = 1;  
		    newOpts.inSampleSize = be;//设置缩放比例  
		    //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
		    isBm = new ByteArrayInputStream(baos.toByteArray());  
		    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
		    return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
		}  
	 /**
	  * 
	  * 截取时间格式yyyy-mm-dd;
	  * */
	 public static String setDataTime(String dataTime) {

			String time = dataTime.substring(0, 10);
			return time;
		}
}


