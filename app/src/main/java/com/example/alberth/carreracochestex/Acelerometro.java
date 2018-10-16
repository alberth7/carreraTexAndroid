package com.example.alberth.carreracochestex;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Acelerometro extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor == null)
            finish();

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorevent) {
                float x = sensorevent.values[0];

                System.out.println("Valor GiroX" + x);
                if (x < -5 ) {
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }else {
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                }
                if (x > 5) {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

    }


    private void Start() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void Stop() {
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Start();
    }
}
