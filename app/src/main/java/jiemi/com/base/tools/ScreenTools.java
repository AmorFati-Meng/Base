package jiemi.com.base.tools;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaRouter;

import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import jiemi.com.base.widget.LauncherSecondScreen;

/**
 * 类名：
 * <p/>
 * 描述：
 *
 * @author：NIU Date：2016/7/29
 */
public class ScreenTools {
    private LauncherSecondScreen mPresentation;

    private boolean mPaused;
    private Context context;
    private MediaRouter mMediaRouter;

    @SuppressLint("NewApi")
    public ScreenTools(LauncherSecondScreen mPresentation,boolean mPaused,Context context,MediaRouter mMediaRouter){
        this.mPresentation=mPresentation;
        this.mPaused=mPaused;
        this.context=context;
        this.mMediaRouter=mMediaRouter;

        mMediaRouter.addCallback(MediaRouter.ROUTE_TYPE_LIVE_VIDEO, mMediaRouterCallback);

        // Update the presentation based on the currently selected route.
        mPaused = false;
        updatePresentation();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("NewApi")
    private void updatePresentation() {


        MediaRouter.RouteInfo route = mMediaRouter.getSelectedRoute(
                MediaRouter.ROUTE_TYPE_LIVE_VIDEO);
        Display presentationDisplay = route != null ? route.getPresentationDisplay() : null;


        if (mPresentation != null && mPresentation.getDisplay() != presentationDisplay) {

            mPresentation.dismiss();
            mPresentation = null;
        }


        if (mPresentation == null && presentationDisplay != null) {

            mPresentation = new LauncherSecondScreen(context, presentationDisplay);
            mPresentation.setOnDismissListener(mOnDismissListener);
            try {

                mPresentation.show();
            } catch (WindowManager.InvalidDisplayException ex) {

                mPresentation = null;
            }
        }

        // Update the contents playing in this activity.
        updateContents();
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("NewApi")
    private void updateContents() {

        if (mPresentation != null) {


            if (mPaused) {

                mPresentation.dismiss();
            } else {

                mPresentation.show();
            }
        } else {

        }
    }
    /**
     * Listens for when presentations are dismissed.
     */
    private final DialogInterface.OnDismissListener mOnDismissListener =
            new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (dialog == mPresentation) {

                        mPresentation = null;
                        updateContents();
                    }
                }
            };

    @SuppressLint("NewApi")
    private final MediaRouter.SimpleCallback mMediaRouterCallback =new MediaRouter.SimpleCallback(){
        @Override
        public void onRouteSelected(MediaRouter router, int type, MediaRouter.RouteInfo info) {

            updatePresentation();
        }

        @Override
        public void onRouteUnselected(MediaRouter router, int type, MediaRouter.RouteInfo info) {

            updatePresentation();
        }

        @Override
        public void onRoutePresentationDisplayChanged(MediaRouter router, MediaRouter.RouteInfo info) {

            updatePresentation();
        }    };


}
