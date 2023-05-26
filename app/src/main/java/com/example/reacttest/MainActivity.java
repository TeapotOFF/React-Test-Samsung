package com.example.reacttest;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private BroadcastReceiver screenOffReceiver;
    private TextView faqTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.back_mus);
        mediaPlayer.setLooping(true); // Установите зацикливание для фоновой музыки
        mediaPlayer.start();

        // Создаем BroadcastReceiver для обнаружения изменений состояния блокировки экрана
        screenOffReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    // Экран заблокирован, останавливаем воспроизведение музыки
                    mediaPlayer.pause();
                } else if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    // Экран разблокирован, возобновляем воспроизведение музыки
                    mediaPlayer.start();
                }
            }
        };

        // Регистрируем BroadcastReceiver для ACTION_SCREEN_OFF и ACTION_SCREEN_ON
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenOffReceiver, filter);

        // находим кнопку FAQ и добавляем обработчик
        Button faqButton = findViewById(R.id.faq_button);
        faqTextView = findViewById(R.id.faq_text);
        faqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ssss");
                if (faqTextView.getVisibility() == View.VISIBLE){
                    faqTextView.setVisibility(View.INVISIBLE);
                }
                else faqTextView.setVisibility(View.VISIBLE);
            }
        });


        // находим кнопку "Start" и добавляем обработчик событий
        Button mStartButton = findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(1);
            }
        });

        Button mStartButton2 = findViewById(R.id.start_button2);
        mStartButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(2);
            }
        });

        Button mStartButton3 = findViewById(R.id.start_button3);
        mStartButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(3);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        // Удаляем BroadcastReceiver при завершении активности
        if (screenOffReceiver != null) {
            unregisterReceiver(screenOffReceiver);
        }
    }

    private void startGame(int gameID) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        Intent intent2 = new Intent(MainActivity.this, Game2Activity.class);
        Intent intent3 = new Intent(MainActivity.this, Game3Activity.class);
        // запускаем игру
        switch (gameID){
            case 1:
                startActivity(intent);
                break;
            case 2:
                startActivity(intent2);
                break;
            case 3:
                startActivity(intent3);
                break;
        }
    }
}
