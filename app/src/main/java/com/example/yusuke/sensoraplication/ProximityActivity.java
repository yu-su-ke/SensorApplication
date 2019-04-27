package com.example.yusuke.sensoraplication;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProximityActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView textView;
    private ImageView imageView1;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        Intent intent = getIntent();
        String text = intent.getStringExtra("message");

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        this.textView = (TextView) findViewById(R.id.textView);
        this.textView.setText(text);

        imageView1 = (ImageView) findViewById(R.id.imageView6);
    }

    //onResumeの実装
    @Override
    protected void onResume() {
        super.onResume();
        //センサを指定してリストを取得
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_PROXIMITY);

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

    @Override
    public void onSensorChanged(final SensorEvent event) {
        // 取得したいセンサイベントの取得
        // ここでは、照度センサSensor.TYPE_LIGHTを指定する
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            // センサ値の取得
            int value = (int) event.values[0];
            // TextViewへ表示
                if (value == 5) {
                    textView.setText("なるべく顔をスマホの画面に近づけないようにね");
                    imageView1.setScaleX(1);
                    imageView1.setScaleY(1);
                } else if (value == 0) {
                    textView.setText("ちっ、近い…");
                    imageView1.setScaleX(3);
                    imageView1.setScaleY(3);
                }
            }
        }

        // onAccuracyChangedの実装
        // onAccuracyChangedはセンサの精度が変更されると呼ばれる
        // 今回は使わない
        @Override
        public void onAccuracyChanged ( final Sensor sensor, final int accuracy){
        }
}
