package jiemi.com.base.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import jiemi.com.base.R;
import jiemi.com.base.application.BaseApplication;

public class HomeAty extends BaseAty {


    @Override
    public View setView() {
        View view=View.inflate(BaseApplication.getApplication(),R.layout.activity_home_aty,null);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void processMsg(String json) {

    }

    @Override
    public void onClick(View v) {

    }
}
