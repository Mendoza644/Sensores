package com.example.sensores;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private SensorEventListener mSensorEventListener;
    private TextView mTextView;
    private TextView mTextView1;
    private TextView mTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.text_view);
        mTextView1 = findViewById(R.id.text_view1);
        mTextView2 = findViewById(R.id.text_view2);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (mSensor == null) {
            Toast.makeText(this, getString(R.string.sensor_message), Toast.LENGTH_SHORT).show();
        }

        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                if (x <= -3) {
                    mTextView.setText(getString(R.string.left_message) + x);
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorLeft));
                    mTextView.setTextColor(Color.WHITE);
                } else if (x > 3) {
                    mTextView.setText(getString(R.string.right_message) + x);
                    mTextView.setTextColor(Color.WHITE);
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorRight));
                } else if (y <= -3) {
                    mTextView1.setText(getString(R.string.back_message) + y);
                    mTextView1.setTextColor(Color.WHITE);
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorDown));
                } else if (y > 3) {
                    mTextView1.setText(getString(R.string.front_message) + y);
                    mTextView1.setTextColor(Color.WHITE);
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorUp));
                } else if (z <= -3) {
                    mTextView2.setText(getString(R.string.down_message) + z);
                    mTextView2.setTextColor(Color.WHITE);
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorDown));
                } else if (z > 3) {
                    mTextView2.setText(getString(R.string.up_message) + z);
                    mTextView2.setTextColor(Color.WHITE);
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorUp));
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        start();
    }

    private void start() {
        mSensorManager.registerListener(mSensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stop() {
        mSensorManager.unregisterListener(mSensorEventListener);
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }
}

