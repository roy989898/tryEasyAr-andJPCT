/**
* Copyright (c) 2015-2016 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
* EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
* and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
*/

package cn.easyar.samples.helloarmultitargetmt;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;

import cn.easyar.engine.EasyAR;


public class MainActivity extends ActionBarActivity{

    /*
    * Steps to create the key for this sample:
    *  1. login www.easyar.com
    *  2. create app with
    *      Name: HelloARMultiTarget-MT
    *      Package Name: cn.easyar.samples.helloarmultitargetmt
    *  3. find the created item in the list and show key
    *  4. set key string bellow
    */
    static String key = "AoNj8Mlzo2I4eBqFr7XaQr5xrmAcolZkhyKgQ5ZsMbjcdEAWRjJOd8u93Eeb8BnK2mEC7xjeXDYknwWcqkBU4B577V8ZS7iTwqVa1cdda2841caba6e684a8a18d256d8c0fvdlrn26A6OTE18ljG4Oxl3t40u7zQvSEYTNBme3tP0ycbnl42QiQTEybUTQyjgrUjEpe";

    static {
        System.loadLibrary("EasyAR");
        System.loadLibrary("HelloARMultiTargetMTNative");
    }

    private GestureDetector mGestureDetector;
    private Renderer mRenderer;

    public static native void nativeInitGL();
    public static native void nativeResizeGL(int w, int h);
    public static native float[] nativeRender();
    private native boolean nativeInit();
    private native void nativeDestory();
    private native void nativeRotationChange(boolean portrait);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mGestureDetector = new GestureDetector(this, new GestureListener());

        EasyAR.initialize(this, key);
        nativeInit();

        GLView glView = new GLView(this);
        glView.setRenderer(new Renderer(this));
        glView.setZOrderMediaOverlay(true);

        ((ViewGroup) findViewById(R.id.preview)).addView(glView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        nativeRotationChange(getWindowManager().getDefaultDisplay().getRotation() == android.view.Surface.ROTATION_0);
    }

    // Process Single Tap event to trigger autofocus
    private class GestureListener extends
            GestureDetector.SimpleOnGestureListener
    {

        @Override
        public boolean onDown(MotionEvent e)
        {
            return true;
        }


        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {


            mRenderer.touchObject(Math.round(e.getX()), Math.round(e.getY()));


            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        nativeRotationChange(getWindowManager().getDefaultDisplay().getRotation() == android.view.Surface.ROTATION_0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nativeDestory();
    }
    @Override
    protected void onResume() {
        super.onResume();
        EasyAR.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EasyAR.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
