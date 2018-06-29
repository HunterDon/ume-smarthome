package com.umeinfo.sdk.smarthometest;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.socks.library.KLog;
import com.umeinfo.smarthome.callback.DeviceCallback;
import com.umeinfo.smarthome.callback.UCallback;
import com.umeinfo.smarthome.exception.ServerHostNotConfigException;
import com.umeinfo.smarthome.manager.DeviceManager;
import com.umeinfo.smarthome.manager.UserManager;
import com.umeinfo.smarthome.mqtt.model.device.Device;
import com.umeinfo.smarthome.mqtt.model.device.DeviceControl;
import com.umeinfo.smarthome.mqtt.model.device.StatusLevel;
import com.umeinfo.smarthome.utils.UCommon;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String USER_NAME = "15601750496";
    public static final String PASSWORD = "q123456";
    private Button btnLoginSDK,btn_getDevices,btn_control;

    List<Device> devices=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnLoginSDK= (Button) findViewById(R.id.btn_login_SDK);
        btn_getDevices= (Button) findViewById(R.id.btn_getDevices);
        btn_control= (Button) findViewById(R.id.btn_control);

        btnLoginSDK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                UserManager.loginSDK(UCommon.getLicense(getApplication()), USER_NAME, new UCallback() {
                    @Override
                    public void onSuccess(int status, String data) {
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(int errCode, String errMsg) {
                        Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btn_getDevices.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DeviceManager.getDeviceList(new DeviceCallback() {
                    @Override
                    public void onSuccess(List<Device> data) {
                        devices.addAll(data);
                        for(Device device:data){
                            KLog.d(device.toString());
                        }
                    }
                    @Override
                    public void onFailure(int errCode, String errMsg) {

                    }
                });

            }
        });

        btn_control.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(devices!=null&&devices.size()>0){
                    DeviceControl deviceControl=new DeviceControl(new DeviceControl.DeviceBean(new StatusLevel(devices.get(0),1),"gateway",1));
                    DeviceManager.controlDevice(deviceControl, new UCallback() {
                        @Override
                        public void onSuccess(int status, String data) {
                            KLog.d("status="+status+"    data="+data);
                        }

                        @Override
                        public void onFailure(int errCode, String errMsg) {

                        }
                    });
                }
            }
        });


    }

}
