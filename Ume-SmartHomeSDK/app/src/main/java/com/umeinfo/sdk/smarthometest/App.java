package com.umeinfo.sdk.smarthometest;

import android.app.Application;
import android.widget.Toast;

import com.umeinfo.smarthome.callback.UCallback;
import com.umeinfo.smarthome.manager.UOAManager;

/**
 * Description:
 * author LuoShuai
 * date 2017/7/5 0005 16:25
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化SDK
        UOAManager.init(this, true, new UCallback() {
            @Override
            public void onSuccess(int status, String data) {
                Toast.makeText(App.this, "UOAManager初始化成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int errCode, String errMsg) {
                Toast.makeText(App.this, "UOAManager初始化失败", Toast.LENGTH_SHORT).show();
            }
        });

//        Stetho.initializeWithDefaults(this);
    }
}
