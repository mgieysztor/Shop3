package com.offcasoftware.shop2.view;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.offcasoftware.shop2.R;

public class Test extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, BlankFragment.newInstance(null, null))
                .add(BlankFragment.newInstance(null, null), "TAG")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
