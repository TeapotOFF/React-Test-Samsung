package com.example.reacttest;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private FrameLayout mLayout;
    private TextView mTimeView;
    private TextView mScoreView;
    private Button mMenuButton;
    private CountDownTimer mTimer;
    private ArrayList<Circle> mCircles = new ArrayList<>();
    private int mHits = 0;
    private int mMisses = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mLayout = findViewById(R.id.game_layout);
        mTimeView = findViewById(R.id.time_view);
        mScoreView = findViewById(R.id.score_view);
        mMenuButton = findViewById(R.id.menu_button);

        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateScoreView();

        mTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                // обновляем отображение времени
                mTimeView.setText("Time: " + millisUntilFinished / 1000);

                // создаем новый круг и добавляем его в RelativeLayout
                Circle circle = new Circle(GameActivity.this, null);
                mLayout.addView(circle);
                mCircles.add(circle);

                circle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // удаляем круг из RelativeLayout и увеличиваем счетчик попаданий
                        mLayout.removeView(v);
                        mCircles.remove(v);
                        mHits++;
                        updateScoreView();
                    }
                });

                mLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMisses++;
                        updateScoreView();
                    }
                });

                // удаляем круги через 3 секунды после их появления
                new CountDownTimer(3000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        if (mCircles.contains(circle)) {
                            mCircles.remove(circle);
                            mMisses++;
                            updateScoreView();
                            mLayout.removeView(circle);
                        }
                    }
                }.start();
            }


            public void onFinish() {
                // показываем результаты и даем возможность выйти в меню
                mTimeView.setText("Time: 0");
                mScoreView.setText("Hits: " + mHits + ", Misses: " + mMisses);
                mMenuButton.setVisibility(View.VISIBLE);
                for (Circle c : mCircles) {
                    mLayout.removeView(c);
                }
            }
        }.start();
    }

    private void updateScoreView() {
        // обновляем отображение счета
        mScoreView.setText("Hits: " + mHits + ", Misses: " + mMisses);
    }
}

