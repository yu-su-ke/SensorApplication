package com.example.yusuke.sensoraplication;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static com.example.yusuke.sensoraplication.R.id.textView;

public class LightActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private TextView textView;
    private ImageView imageView1;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        Intent intent = getIntent();
        String text = intent.getStringExtra("message");

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        this.textView= (TextView) findViewById(R.id.textView);
        this.textView.setText(text);

        imageView1 = (ImageView)findViewById(R.id.imageView);
    }

    //onResumeの実装
    @Override
    protected void onResume() {
        super.onResume();
        //センサを指定してリストを取得
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_LIGHT);

        //リスナーの登録
        //リスナの登録しないとアプリケーションにセンサ情報を通知できない
        //第1引数にはリスナー、第2引数にはセンサの種類、第3引数には取得する頻度を指定する
        if (sensors.size() > 0) {
            //リスナーの登録
            //リスナの登録しないとアプリケーションにセンサ情報を通知できない
            // 第1引数にはリスナー、第2引数にはセンサの種類、第3引数には取得する頻度を指定する
            sensorManager.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_UI);
        }
    }

    // アプリがバックグラウンドのときはバッテリーの消費を抑えるためにリスナーを解除する
    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            // リスナーの登録解除
            sensorManager.unregisterListener(this);
        }
    }

    // onSensorChangedの実装
    // onSensorChangedはセンサの値が変わったら呼ばれる
    // 取得したいセンサイベントの取得
    // ここでは、照度センサSensor.TYPE_AMBIENT_TEMPERATUREを指定する
    // センサ値の取得
    // TextViewへ表示
    @Override
    public void onSensorChanged(final SensorEvent event) {
        // 取得したいセンサイベントの取得
        // ここでは、照度センサSensor.TYPE_LIGHTを指定する
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            // センサ値の取得
            int value = (int) event.values[0];
            // TextViewへ表示
            if (value <= 150) {
                textView.setText("周りの光度は" + String.valueOf(value) + "だよ\r\n" + "スマホは明るいところで操作しよう");
                imageView1.setImageResource(R.drawable.neko1);
            } else if (value > 150 && value <= 600) {
                textView.setText("周りの光度は" + String.valueOf(value) + "だよ\r\n" + "目の疲れには注意して");
                imageView1.setImageResource(R.drawable.neko2);
            } else {
                textView.setText("周りの光度は" + String.valueOf(value) + "だよ\r\n" + "まっ、まぶしい…");
                imageView1.setImageResource(R.drawable.neko3);
            }
        }
    }

    // onAccuracyChangedの実装
    // onAccuracyChangedはセンサの精度が変更されると呼ばれる
    // 今回は使わない
    @Override
    public void onAccuracyChanged(final Sensor sensor, final int accuracy) {
    }
}
