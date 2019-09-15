package com.mdurkovic.organizeyourself.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mdurkovic.organizeyourself.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class VideoActivity extends AppCompatActivity {

    private List<String> myList = new ArrayList<>();
    private GridView grid;
    private String currentPhotoPath;
    FloatingActionButton btnCamera;
    String pathToFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        grid = findViewById(R.id.grid);
        grid.setAdapter(new CustomGrid(myList, this));


        // camera permissions


        btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePhoto();
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(currentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);

            myList.add(currentPhotoPath);
            grid.invalidateViews();
        }
    }


    private void TakePhoto() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createPhotoFile();

            } catch (IOException ex) {
//                e.printStackTrace();
            }

            if (photoFile != null) {

                pathToFile = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(VideoActivity.this, "com.mdurkovic.organizeyourself.fileprovider", photoFile);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePicture, 1);
            }
        }
    }

    private File createPhotoFile() throws IOException {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(name, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;

    }

    public class CustomGrid extends BaseAdapter {

        private List<String> myList;
        private Context mContext;

        public CustomGrid(List<String> theList, Context c) {
            this.mContext = c;
            this.myList = theList;
        }
        @Override
        public int getCount() {

            if(myList != null)
                return myList.size();
            else
                return 0;
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            ImageView imageView;
            if (view == null) {

                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            }
            else {
                imageView = (ImageView) view;
            }

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = 4;
            bmOptions.inPurgeable = true;
            Bitmap bitmap = BitmapFactory.decodeFile(myList.get(position), bmOptions);

            imageView.setImageBitmap(bitmap);

            return imageView;

        }
    }
}

