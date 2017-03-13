package com.offcasoftware.shop2.view.widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.offcasoftware.shop2.R;

public class TestStorageActivity extends AppCompatActivity {

    @BindView(R.id.files_dir)
    TextView mInternalDir;

    @BindView(R.id.cache_dir)
    TextView mCacheDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_storage);

        ButterKnife.bind(this);

        setup();
    }

    private void setup() {
        mInternalDir.setText("Internal Dir: " + getFilesDir().getAbsolutePath());
        mCacheDir.setText("Cache Dir: " + getCacheDir().getAbsolutePath());
    }
}
