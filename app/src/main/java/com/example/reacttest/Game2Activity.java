package com.example.reacttest;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Game2Activity extends AppCompatActivity {

    private View mBackgroundView;
    private boolean mIsGreen = false;
    private long mStartTime = 0;
    private Handler mHandler = new Handler();

    private Runnable mResetRunnable = new Runnable() {
        @Override
        public void run() {
            // Сбросить таймер
            mStartTime = System.currentTimeMillis();
            // Установить фоновый цвет белым
            mBackgroundView.setBackgroundColor(Color.WHITE);
            // Запустить таймер снова
            mHandler.postDelayed(mChangeColorRunnable, 5000);
        }
    };

    private Runnable mChangeColorRunnable = new Runnable() {
        @Override
        public void run() {
            // Изменить фоновый цвет на зеленый
            mBackgroundView.setBackgroundColor(Color.GREEN);
            mIsGreen = true;
            mStartTime = System.currentTimeMillis();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        mBackgroundView = findViewById(R.id.background_view);

        // Запустить таймер
        mHandler.postDelayed(mChangeColorRunnable, 5000);

        // Добавить слушатель нажатия на фоновый элемент
        mBackgroundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsGreen) {
                    // Вывести время от появления зеленого экрана до нажатия
                    long timeTaken = System.currentTimeMillis() - mStartTime;
                    Toast.makeText(Game2Activity.this, "Time taken: " + timeTaken + " ms", Toast.LENGTH_SHORT).show();
                    // Сбросить таймер и установить фоновый цвет белым
                    mHandler.removeCallbacks(mResetRunnable);
                    mHandler.postDelayed(mResetRunnable, 2000);
                    mIsGreen = false;
                } else {
                    // Изменить фоновый цвет на красный и сбросить таймер
                    mBackgroundView.setBackgroundColor(Color.RED);
                    mHandler.removeCallbacks(mChangeColorRunnable);
                    mHandler.removeCallbacks(mResetRunnable);
                    mHandler.postDelayed(mResetRunnable, 2000);
                }
            }
        });
    }
}
