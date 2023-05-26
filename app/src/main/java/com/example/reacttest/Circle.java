package com.example.reacttest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.Random;

public class Circle extends View {
    private ShapeDrawable mCircle;
    private int mRadius;
    private int mColor;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);

        // генерируем случайный радиус и цвет для круга
        Random random = new Random();
        mRadius = random.nextInt(50) + 50;
        mColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));

        // создаем круг с заданными параметрами
        mCircle = new ShapeDrawable(new OvalShape());
        mCircle.getPaint().setColor(mColor);
        mCircle.setBounds(0, 0, mRadius * 2, mRadius * 2);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mRadius * 2, mRadius * 2);
        setLayoutParams(layoutParams);

        // добавляем круг в Layout
        setX(random.nextInt(context.getResources().getDisplayMetrics().widthPixels - mRadius * 2));
        setY(random.nextInt((context.getResources().getDisplayMetrics().heightPixels - mRadius * 2) - mRadius * 2 ) + mRadius * 2);
    }

    public int getRadius() {
        return mRadius;
    }

    public int getColor() {
        return mColor;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        super.setOnClickListener(listener);
    }

    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        mCircle.draw(canvas);
    }
}
