package com.example.iimtaligarh.imageslider;

import static com.google.common.collect.ComparisonChain.start;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.iimtaligarh.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private ArrayList<slider_item> mSliderItems;
    private SliderAdapterVH viewHolder;

    public SliderAdapterExample(Context context) {
        this.context = context;
        mSliderItems = new ArrayList<>();

        mSliderItems.add(new slider_item("https://firebasestorage.googleapis.com/v0/b/iimt-aligarh-f4e1d.appspot.com/o/one.jpg?alt=media&token=fe5796eb-0e53-4ebb-8e99-2f88d4f7cef1"));
        mSliderItems.add(new slider_item("https://firebasestorage.googleapis.com/v0/b/iimt-aligarh-f4e1d.appspot.com/o/two.png?alt=media&token=92958fa0-aece-4cd7-bb63-4832b9b9fb52"));
        mSliderItems.add(new slider_item("https://firebasestorage.googleapis.com/v0/b/iimt-aligarh-f4e1d.appspot.com/o/three.png?alt=media&token=efb74200-ad70-47ff-9e20-5b5e9e7a0e79"));
        mSliderItems.add(new slider_item("https://firebasestorage.googleapis.com/v0/b/iimt-aligarh-f4e1d.appspot.com/o/four.png?alt=media&token=86ce96c2-940f-4680-9723-6a8f7e200cb8"));
        mSliderItems.add(new slider_item("https://firebasestorage.googleapis.com/v0/b/iimt-aligarh-f4e1d.appspot.com/o/five.png?alt=media&token=d56d7380-4b29-44ba-97e1-1e37d70dd0aa"));

    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        this.viewHolder = viewHolder;
        File path = context.getFilesDir();

        switch (position) {
            case 0:
                File file = new File(path, "A.PNG");
                if (file.exists()) {
                     Bitmap bitmap;
                    InputStream inputStream = null;
                    try {
                        inputStream = context.openFileInput("A.PNG");
                        bitmap = BitmapFactory.decodeStream(inputStream);

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Glide.with(viewHolder.itemView)
                            .load(bitmap)
                            .fitCenter()
                            .into(viewHolder.imageViewBackground);
                }else {
                    new Thread(() -> {
                        sliderData d = new sliderData(mSliderItems.get(position).getImageUrl(), "A");
                        d.execute();
                    }).start();

                }
                break;
            case 1:
                File file1 = new File(path, "B.PNG");
                if (file1.exists()) {
                    Bitmap bitmap1;
                    InputStream inputStream = null;
                    try {
                        inputStream = context.openFileInput("B.PNG");
                        bitmap1 = BitmapFactory.decodeStream(inputStream);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Glide.with(viewHolder.itemView)
                            .load(bitmap1)
                            .fitCenter()
                            .into(viewHolder.imageViewBackground);
                } else {
                    new Thread(() -> {
                        sliderData d2 = new sliderData(mSliderItems.get(position).getImageUrl(), "B");
                        d2.execute();
                    }).start();
                }
                break;
            case 2:
                File file2 = new File(path, "C.PNG");
                if (file2.exists()) {
                    Bitmap bitmap2;
                    InputStream inputStream = null;
                    try {
                        inputStream = context.openFileInput("C.PNG");
                        bitmap2 = BitmapFactory.decodeStream(inputStream);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Glide.with(viewHolder.itemView)
                            .load(bitmap2)
                            .fitCenter()
                            .into(viewHolder.imageViewBackground);

                } else {
                    new Thread(() -> {
                        sliderData d3 = new sliderData(mSliderItems.get(position).getImageUrl(), "C");
                        d3.execute();
                    }).start();
                }
                break;
            case 3:
                File file3 = new File(path, "D.PNG");
                if (file3.exists()) {
                    Bitmap bitmap3;
                    InputStream inputStream = null;
                    try {
                        inputStream = context.openFileInput("D.PNG");
                        bitmap3 = BitmapFactory.decodeStream(inputStream);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Glide.with(viewHolder.itemView)
                            .load(bitmap3)
                            .fitCenter()
                            .into(viewHolder.imageViewBackground);
                } else {
                    new Thread(() -> {
                        sliderData d4 = new sliderData(mSliderItems.get(position).getImageUrl(), "D");
                        d4.execute();
                    }).start();
                }
                break;

            case 4:
                File file4 = new File(path, "E.PNG");
                if (file4.exists()) {
                    Bitmap bitmap4;
                    InputStream inputStream = null;
                    try {
                        inputStream = context.openFileInput("E.PNG");
                        bitmap4 = BitmapFactory.decodeStream(inputStream);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                            Glide.with(viewHolder.itemView)
                                    .load(bitmap4)
                                    .fitCenter()
                                    .into(viewHolder.imageViewBackground);

                } else {
                    new Thread(() -> {
                        sliderData d5 = new sliderData(mSliderItems.get(position).getImageUrl(), "E");
                        d5.execute();
                    }).start();
                }
                break;
        }


        viewHolder.itemView.setOnClickListener(v -> Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show());
    }

    public int getCount() {
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;
        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            this.itemView = itemView;
        }
    }


    class sliderData extends AsyncTask<Void, Void, Bitmap> {

        String url;
        String filename;

        sliderData(String url, String filename) {
            this.url = url;
            this.filename = filename;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap myBitmap;
            try {
                URL connection = new URL(url);
                InputStream input = connection.openStream();
                myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
            }

            return null;
        }

        @SuppressLint("WrongThread")
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                File path = context.getFilesDir();
                File file = new File(path, filename + ".PNG");
                OutputStream out = null;
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

        }


        public void readData() {
            Bitmap bitmap = null;
            File path = context.getFilesDir();
            File file = new File(path, filename + ".PNG");
            if (file.exists()) {
                InputStream inputStream = null;
                try {
                    inputStream = context.openFileInput(filename + ".PNG");
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Glide.with(viewHolder.itemView)
                        .load(bitmap)
                        .centerCrop()
                        .into(viewHolder.imageViewBackground);
            }


        }

    }

}
