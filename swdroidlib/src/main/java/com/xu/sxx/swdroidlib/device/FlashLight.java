package com.xu.sxx.swdroidlib.device;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

/**
 * Fun:手电筒
 * ①需要权限：
 * <uses-permission android:name="android.permission.CAMERA" />
 * <uses-permission android:name="android.permission.FLASHLIGHT" />
 * <uses-feature android:name="android.hardware.camera" />
 * <uses-feature android:name="android.hardware.camera.flash" />
 *
 * ② LED Flash Light应用程序只能在人像模式下工作,因此在activity标签添加如下配置
 * android:screenOrientation="portrait"
 * Created by sxx.xu on 7/4/2017.
 */

public class FlashLight {

    private static Context mContext;
    private static volatile FlashLight instance = null;
    private CameraManager mCameraManager;
    private android.hardware.Camera mCamera;

    private String[] camerList;

    private FlashLight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mCameraManager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
            try {
                camerList = mCameraManager.getCameraIdList();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            mCamera = android.hardware.Camera.open();
        }
    }

    public static FlashLight getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            synchronized (FlashLight.class) {
                if (instance == null) {
                    instance = new FlashLight();
                }
            }
        }
        return instance;
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void turnOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String torch : camerList) {
                try {
                    mCameraManager.setTorchMode(torch, true);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (mCamera == null) {
                mCamera = android.hardware.Camera.open();
            }
            final Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameters);
            mCamera.startPreview();
        }
    }

    public void turnOff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String torch : camerList) {
                try {
                    mCameraManager.setTorchMode(torch, false);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (mCamera != null) {
                mCamera.startPreview();
                mCamera.release();
                mCamera = null;
            }
        }
    }
}
