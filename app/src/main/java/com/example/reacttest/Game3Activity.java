package com.example.reacttest;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Game3Activity extends AppCompatActivity{

    private TextView scoreTextView;
    private TextView letterTextView;
    private TextView timerTextView;
    private Button btnMenu;
    private InputMethodManager imm;
    private int score;
    private boolean gameRunning;

    private static final int GAME_DURATION = 30000; // Длительность игры в миллисекундах
    private static final int TIMER_INTERVAL = 1000; // Интервал обновления таймера в миллисекундах

    private final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private CountDownTimer timer = new CountDownTimer(GAME_DURATION, TIMER_INTERVAL) {
        @Override
        public void onTick(long millisUntilFinished) {
            // Обновляем оставшееся время на экране
            timerTextView.setText(String.format("Time: %d", millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            // Игра завершена
            gameRunning = false;
            letterTextView.setText("");
            btnMenu.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game3);

        scoreTextView = findViewById(R.id.scoreTextView);
        letterTextView = findViewById(R.id.letterTextView);
        timerTextView = findViewById(R.id.timerTextView);
        btnMenu = findViewById(R.id.btnMenu);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                finish();
            }
        });

        // Установка слушателя нажатий клавиш
        View rootView = findViewById(R.id.rootView);
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (gameRunning && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // Получаем символ, нажатый на клавиатуре
                    String pressedKey = String.valueOf((char) event.getUnicodeChar());

                    // Получаем текущую отображаемую букву
                    String currentLetter = letterTextView.getText().toString();

                    // Проверяем, является ли нажатая клавиша кнопкой нижней панели
                    if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME ||
                            keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_APP_SWITCH) {
                        // Игнорируем обработку нажатия кнопок нижней панели
                        finish();
                        return true;
                    }

                    if (pressedKey.equalsIgnoreCase(currentLetter)) {
                        // Правильное нажатие
                        score++;
                    } else {
                        // Неправильное нажатие
                        score--;
                    }

                    // Обновляем счет
                    updateScore();
                    // Генерируем новую случайную букву
                    generateRandomLetter();
                }

                return true;
            }
        });

        // Начинаем игру при запуске активности
        startGame();

        // Открытие клавиатуры
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        // Установка слушателя закрытия клавиатуры
        letterTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Выполнить переход в основную активность
                onBackPressed();
                return true;
            }
        });
    }

    private void startGame() {
        score = 0;
        gameRunning = true;
        updateScore();
        generateRandomLetter();
        timer.start();
    }

    private void updateScore() {
        scoreTextView.setText(String.format("Score: %d", score));
    }

    private void generateRandomLetter() {
        Random random = new Random();
        int index = random.nextInt(letters.length);
        letterTextView.setText(letters[index]);
    }
}
