package com.example.iimtaligarh.images;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private ProgressBar progressBar;
    private String filename;
    private Context context;
    private Bitmap myBitmap = null;
    private ImageView imageView;


    public ImageLoadTask(String url, ImageView imageView, Context context,ProgressBar progressBar,String name) {
        this.url = url;
        this.imageView = imageView;
        this.context = context;
        this.filename = name;
        this.progressBar = progressBar;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL connection = new URL(url);

            InputStream input = connection.openStream();
            myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
           // Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @SuppressLint("WrongThread")
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        File path = context.getFilesDir();
        File file = new File(path, filename);
        OutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            readData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void readData(){
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            inputStream = context.openFileInput(filename);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        progressBar.setVisibility(View.INVISIBLE);
        imageView.setImageBitmap(bitmap);
    }




}

