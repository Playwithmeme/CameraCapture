package com.example.cameracapture;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    ImageView im;
    Bitmap b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        im = findViewById(R.id.imageView);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,

        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},1);
    }

    public void Capture(View view) {

        Intent i1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i1,12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12)
        {

            b = (Bitmap) data.getExtras().get("data");
            im.setImageBitmap(b);
        }
    }

    public void SaveExternal(View view) {

        String filename = "abc.jpg";

        try
        {
            File file = new File("/sdcard/" + filename);
            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);

            b.compress(Bitmap.CompressFormat.JPEG,100, fos);

            MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),"Captured Image");

            osw.close();
            fos.close();
            Toast.makeText(getApplicationContext(), "Image Saved as "+filename, Toast.LENGTH_LONG).show();
        }

        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }



}