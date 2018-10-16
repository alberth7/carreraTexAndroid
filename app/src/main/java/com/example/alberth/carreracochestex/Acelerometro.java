package com.example.alberth.carreracochestex;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Acelerometro extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    MiGLSurfaceView superficie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* Orientación de la pantalla vertical (PORTRAIT) u horizontal(LANDSCAPE) */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        superficie = new MiGLSurfaceView(this);
        setContentView(superficie);

        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            superficie.renderiza2.acelerometroX = e.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int arg1) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        superficie.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        superficie.onPause();
        sensorManager.unregisterListener(this);
    }
}


class MiGLSurfaceView extends GLSurfaceView {

    public Renderiza2 renderiza2;

    public MiGLSurfaceView(Context contexto) {
        super(contexto);
        renderiza2 = new Renderiza2();
        setRenderer(renderiza2);
        requestFocus();
        setFocusableInTouchMode(true);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}



