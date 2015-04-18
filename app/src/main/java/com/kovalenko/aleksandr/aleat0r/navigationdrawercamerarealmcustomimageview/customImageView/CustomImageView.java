package com.kovalenko.aleksandr.aleat0r.navigationdrawercamerarealmcustomimageview.customImageView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomImageView extends ImageView {

    // Радиус закругления угла
    private float cornerRadius;
    // Размер квадратного CustomImageView (посчитан в onMeasure)
    private int size;

    // Проверяем величину аргумента, если проходит, то устанавливаем значение радиуса
    public void setCornerRadius(float cornerRadius) {
        if((cornerRadius <= size/2) & (cornerRadius >=0)){
            this.cornerRadius = cornerRadius;
        }
    }

    // Метод установки радиуса, сохраненного после поворота экрана (проверки нет, так как с
    // проверкой значение не устанавливается)
    public void setCornerRadiusAfterTurn(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // Переопределяем метод onDraw, чтобы была возможность сделать CustomImageView
    // с закругленными углами
    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        // Создаём прямоугольник
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        // Добавляем прямоугольник с закруглёнными углами к объекту Path
        clipPath.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }

    // Переопределяем метод onMeasure, чтобы сделать CustomImageView квадратным
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // Вызываем метод onMeasure класса ImageView, чтобы рассчитать доступные размеры
        // для изображения стандартным образом
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // Получаем рассчитанные размеры, доступные для изображения
        final int height = getMeasuredHeight();
        final int width = getMeasuredWidth();
        // Выбираем меньшее значение
        size = Math.min(height, width);
        // Задаем размеры
        setMeasuredDimension(size, size);
    }
}
