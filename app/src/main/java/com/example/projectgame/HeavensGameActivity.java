package com.example.projectgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class HeavensGameActivity extends AppCompatActivity implements SensorEventListener {

    private HeavensGameView heavensGameView;
    private SensorManager senSensorManager;
    public static float deltax = 0, deltay = 0, deltaz = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Point point = new Point();


        getWindowManager().getDefaultDisplay().getSize(point);

        this.heavensGameView = new HeavensGameView(this, point.x, point.y); // sending the screen size to HeavensGameView


        setContentView(this.heavensGameView);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

    protected void onPause() {   // if another activity comes into the foreground onPause is called
        super.onPause();
        this.heavensGameView.pause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {   // if it comes back from another activity to this activity then onResume will be called
        super.onResume();
        this.heavensGameView.resume();
        senSensorManager.registerListener(this, senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            deltax = event.values[0];
            deltay = event.values[1];
            deltaz = event.values[2];
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}