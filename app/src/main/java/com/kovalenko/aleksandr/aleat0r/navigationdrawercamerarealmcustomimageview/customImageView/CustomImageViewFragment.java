package com.kovalenko.aleksandr.aleat0r.navigationdrawercamerarealmcustomimageview.customImageView;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.kovalenko.aleksandr.aleat0r.navigationdrawercamerarealmcustomimageview.R;

public class CustomImageViewFragment extends Fragment implements View.OnClickListener{

    CustomImageView customImageView;
    float cornerRadius;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_custom_imageview, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            // Получаем сохраненный при повороте экрана радиус скругления углов
            cornerRadius = savedInstanceState.getFloat("cornerRadius");
        }
        // Находим CustomImageView
        customImageView = (CustomImageView)view.findViewById(R.id.customImageView);
        // Обрезаем лишнее
        customImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // Устанавливаем радиус скругления углов(если есть сохраненное
        // значение радиуса после поворота экрана)
        customImageView.setCornerRadiusAfterTurn(cornerRadius);
        // Находим кнопки
        Button buttonPlus = (Button)view.findViewById(R.id.buttonPlus);
        Button buttonMinus = (Button)view.findViewById(R.id.buttonMinus);
        // Присваиваем обработчики кнопкам
        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);

    }

    // Обработка нажатия на кнопки
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonPlus:
                changeCornerRadius(20f);
                break;
            case R.id.buttonMinus:
               changeCornerRadius(-20f);
                break;
        }
    }

    // Метод для увеличения/уменьшения величины радиуса скругления
    public void changeCornerRadius (float f){
        cornerRadius = customImageView.getCornerRadius();
        customImageView.setCornerRadius(cornerRadius + f);
        customImageView.invalidate();

    }

    // Сохраняем радиус скругления, чтобы после поворота экрана
    // картинка была с ранее установленной величиной скругления углов
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("cornerRadius", cornerRadius);
    }
}
