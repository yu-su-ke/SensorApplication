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

public class StepActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView textView;
    private ImageView imageView1;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

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
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_STEP_COUNTER);

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
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            // センサ値の取得
            int value = (int) event.values[0];
            // TextViewへ表示
            if (value <= 5) {
                textView.setText("今の歩数は" + String.valueOf(value) + "歩だよ\r\n" + "今日も一日がんばろー");
                imageView1.setImageResource(R.drawable.bird1);
            } else if (value > 5 && value <= 10) {
                textView.setText("今の歩数は" + String.valueOf(value) + "歩だよ\r\n" + "まだまだこれから");
                imageView1.setImageResource(R.drawable.bird2);
            } else if (value > 10 && value <= 15){
                textView.setText("今の歩数は" + String.valueOf(value) + "歩だよ\r\n" + "そろそろきついかも…");
                imageView1.setImageResource(R.drawable.bird3);
            }else if (value > 15 && value <= 20){
                textView.setText("今の歩数は" + String.valueOf(value) + "歩だよ\r\n" + "…");
                imageView1.setImageResource(R.drawable.bird4);
            }else if (value > 20){
                textView.setText("今の歩数は" + String.valueOf(value) + "歩だよ\r\n" + "どこまでも飛べそう…!");
                imageView1.setImageResource(R.drawable.bird5);
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
