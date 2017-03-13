package com.offcasoftware.shop2.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.offcasoftware.shop2.R;
import com.offcasoftware.shop2.model.Product;

import java.io.*;

public class StorageActivity extends AppCompatActivity {

    @BindView(R.id.files_dir)
    TextView mFilesDirTextView;

    @BindView(R.id.cache_dir)
    TextView mCacheDirTextView;

    @BindView(R.id.saved_string)
    TextView mSavedStringTextView;

    @BindView(R.id.image)
    ImageView mImageView;

    @BindView(R.id.object)
    TextView mObjectTeView;

    private final SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Toast.makeText(StorageActivity.this, sharedPreferences.getString(key, null), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        ButterKnife.bind(this);

        setup();
        internalMemory();
        objectSaving();
        registerOnSharedPreferenceChangeListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener(mListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        preferences.unregisterOnSharedPreferenceChangeListener(mListener);


    }

    private void setup() {
        File filesDir = getFilesDir();
        mFilesDirTextView.setText("Internal Files Dir: \n" + filesDir.getPath());

        File cacheDir = getCacheDir();
        mCacheDirTextView.setText("Cache Files Dir: \n" + cacheDir.getPath());
    }

    private void internalMemory() {
        String filename = "myfile";
        String string = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileInputStream inputStream;
        String fileAsString = null;
        try {
            inputStream = openFileInput(filename);
            BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while(line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            fileAsString = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mSavedStringTextView.setText(fileAsString);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dom1);

        String filename2 = "myimage.png";
        FileOutputStream outputStream2;


        try {
            outputStream2 = openFileOutput(filename2, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream2);
            outputStream2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File f2 = new File(getFilesDir(), filename2);
        if (f2.exists()) {
            Toast.makeText(getApplicationContext(), "EEE", Toast.LENGTH_LONG).show();
        }


        bitmap = null;
        File f = new File(getFilesDir(), filename2);
        if (f.exists()) {
            Toast.makeText(getApplicationContext(), String.valueOf(f.length()), Toast.LENGTH_LONG).show();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            bitmap = BitmapFactory.decodeStream(openFileInput(filename2), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mImageView.setImageBitmap(bitmap);

    }

    private void objectSaving() {
        String filename = "data";
        Product product1 = new Product(1, "dom 1", 1000, "dom1");
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(openFileOutput(filename, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(product1);
            objectOutputStream.close();

            product1 = null;

            FileInputStream fis = openFileInput(filename);
            ObjectInputStream is = new ObjectInputStream(fis);
            product1 = (Product) is.readObject();
            is.close();
            fis.close();

            mObjectTeView.setText(product1.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void testExternal() {

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("LOG", "Directory not created");
        }
        return file;
    }

    public File getAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("LOG", "Directory not created");
        }
        return file;
    }

    public void registerOnSharedPreferenceChangeListener() {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            }
        });
    }

    public void testSharedPreferences() {
        final String key = "KEY";
        final SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        preferences.edit().putString(key, "OLA").apply();
        String value = preferences.getString(key, null);
        Toast.makeText(this, value, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.test_shared)
    public void onButtonClicked(View v) {
        testSharedPreferences();
    }
}
