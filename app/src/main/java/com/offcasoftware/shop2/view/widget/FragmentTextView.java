package com.offcasoftware.shop2.view.widget;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.offcasoftware.shop2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RENT on 2017-03-13.
 */

public class FragmentTextView extends Fragment {

    @BindView(R.id.fragment_textview)
    TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_textview, container, false);

        ButterKnife.bind(this, view);

        return view;
    }
    public void showText (String text){
        mTextView.setText(text);
    }

}
