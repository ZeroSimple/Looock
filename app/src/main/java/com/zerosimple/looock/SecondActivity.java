package com.zerosimple.looock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zerosimple.looock.R;

public class SecondActivity extends Activity {



    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mComponentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mDevicePolicyManager = (DevicePolicyManager)
                getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, com.zerosimple.looock.AdminReceiver.class);

        if(mDevicePolicyManager.isAdminActive(mComponentName)){
            mDevicePolicyManager.lockNow();
            finish();
        }else{
            activeManager();
        }
        setContentView(R.layout.activity_main);
    }



    private void activeManager(){
        Intent i  = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
        i.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Active Device Administrator to start Looock.");
        startActivityForResult(i, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 0 && resultCode == Activity.RESULT_OK){
            mDevicePolicyManager.lockNow();
            finish();
        }else
            activeManager();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
