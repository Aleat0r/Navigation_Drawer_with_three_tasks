package com.kovalenko.aleksandr.aleat0r.navigationdrawercamerarealmcustomimageview.CameraOrGallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kovalenko.aleksandr.aleat0r.navigationdrawercamerarealmcustomimageview.R;

import java.io.File;


public class CameraOrGalleryFragment extends Fragment implements View.OnClickListener{

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;
    ImageView imageView;
    String photoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera_or_gallery, null);
        // Находим imageView и присваиваем ему обработчик нажатий
        imageView = (ImageView)view.findViewById(R.id.imageView);
        imageView.setOnClickListener(this);

        if (savedInstanceState != null) {
            // Получаем сохраненный при повороте экрана путь
            // к сфотографированному/выбранному из галереи изображению
            photoPath = savedInstanceState.getString("photoPath");
            setBitmapToImageView(photoPath);
            if (photoPath == null){
                imageView.setImageResource(R.drawable.photo);
            }
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        // При нажатии по картинке вызываем этот метод
        selectImage();
    }

    private void selectImage() {
        final CharSequence[] options = getResources().getStringArray(R.array.dialog_array);
        // Создаем диалог
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Задаем диалогу заголовок
        builder.setTitle(R.string.dialogTitle);
        // Задаем диалогу пункты и обработчик нажатия на пункты
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(options[0]))
                {
                    // Создаём intent для вызова камеры
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Имя изображения и место куда его сохранить
                    File file = new File(android.os.Environment.getExternalStorageDirectory(), String.valueOf(System.currentTimeMillis()) + ".jpg");
                    // Сохраняем путь к изображению
                    photoPath = file.getAbsolutePath();
                    // Указываем, чтоб в директорию file был сохранен результат работы камеры
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    // Отправляем intent с requestCode
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }
                else if (options[item].equals(options[1]))
                {
                    // Вызываем галерею
                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    // Отправляем intent с requestCode
                    startActivityForResult(intent, GALLERY_REQUEST_CODE);

                }
                else if (options[item].equals(options[2])) {
                    // Закрываем диалог
                    dialog.dismiss();
                }
            }
        });
        // Вызываем диалог
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                // Устнавливаем фотографию в ImageView
                setBitmapToImageView(photoPath);
            } else if (requestCode == GALLERY_REQUEST_CODE) {
                // Получаем путь к выбранному изображению через cursor
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePath[0]);
                photoPath = cursor.getString(columnIndex);
                cursor.close();
                // Устнавливаем выбранное из галереи изображение в ImageView
                setBitmapToImageView(photoPath);
            }
        }
    }

    // Создаём bitmap и устнавливаем его в ImageView
    private void setBitmapToImageView(String photoPath){
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bitmapOptions);
        imageView.setImageBitmap(bitmap);
    }

    // Сохраняем путь к сфотографированному/выбранному из галереи изображению, чтобы после
    // поворота экрана изображение не сменилось на стандартное
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("photoPath", photoPath);
    }
}


