package com.example.yusuke.sensoraplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private ImageView imageView;
    private int preDx, preDy, newDx, newDy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウトファイルとソースコードの紐付け
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setOnTouchListener(this);

        // 画面遷移用のIntentを作成
        final Intent intent = new Intent(this, PressureActivity.class);
        final Intent intent2 = new Intent(this, LightActivity.class);
        final Intent intent3 = new Intent(this, ProximityActivity.class);
        final Intent intent4 = new Intent(this, StepActivity.class);

        // Buttonのインスタンスをレイアウトから取得
        Button button = (Button) findViewById(R.id.button);
        // Buttonにクリック処理を実装
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intentにデータを付与。Activity間はこの方法でしかデータの受け渡しが不可能
                intent.putExtra("message", "");
                // 画面遷移開始
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        // Buttonにクリック処理を実装
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intentにデータを付与。Activity間はこの方法でしかデータの受け渡しが不可能
                intent2.putExtra("message", "");
                // 画面遷移開始
                startActivity(intent2);
            }
        });

        Button button3 = (Button) findViewById(R.id.button3);
        // Buttonにクリック処理を実装
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intentにデータを付与。Activity間はこの方法でしかデータの受け渡しが不可能
                intent3.putExtra("message", "");
                // 画面遷移開始
                startActivity(intent3);
            }
        });

        Button button4 = (Button) findViewById(R.id.button4);
        // Buttonにクリック処理を実装
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intentにデータを付与。Activity間はこの方法でしかデータの受け渡しが不可能
                intent4.putExtra("message", "");
                // 画面遷移開始
                startActivity(intent4);
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // x,y 位置取得
        newDx = (int)event.getRawX();
        newDy = (int)event.getRawY();

        switch (event.getAction()) {
            // タッチダウンでdragされた
            case MotionEvent.ACTION_MOVE:
                // ACTION_MOVEでの位置
                int dx = imageView.getLeft() + (newDx - preDx);
                int dy = imageView.getTop() + (newDy - preDy);

                // 画像の位置を設定する
                imageView.layout(dx, dy, dx + imageView.getWidth(), dy + imageView.getHeight());
                break;
        }

        // タッチした位置を古い位置とする
        preDx = newDx;
        preDy = newDy;

        return true;
    }
}

