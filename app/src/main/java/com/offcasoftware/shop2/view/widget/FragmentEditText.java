package com.offcasoftware.shop2.view.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.offcasoftware.shop2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RENT on 2017-03-13.
 */

public class FragmentEditText extends Fragment {

    @BindView(R.id.fragment_edittext)
    EditText mText;

    public interface OnButtonClicked {
        void showText(String text);
    }

    OnButtonClicked mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_edittext,container,false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnButtonClicked) {
            mListener  = (OnButtonClicked) activity;
        }
    }
    @OnClick(R.id.fragment_button)
    public void nButtonClicked(View v) {
        if (mListener != null) {
            mListener.showText(mText.getText().toString());
        }
    }



}
