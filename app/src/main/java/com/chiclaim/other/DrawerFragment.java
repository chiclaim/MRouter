package com.chiclaim.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chiclaim.modularization.R;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/2.
 */

public class DrawerFragment extends Fragment {
    static int count;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.drawer_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn = (Button) view.findViewById(R.id.text_right);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSlide();
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            int count = bundle.getInt("count");
            btn.setText("index:" + count);
        } else {
            btn.setText("index:" + 0);
        }

        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSlide();
            }
        });
    }

    public void removeSlide() {
        if (getActivity() instanceof DrawerLayoutActivity) {
            DrawerLayoutActivity myActivity = (DrawerLayoutActivity) getActivity();
            myActivity.removeSlide();
        }
    }

    public void addSlide() {
        if (getActivity() instanceof DrawerLayoutActivity) {
            DrawerLayoutActivity myActivity = (DrawerLayoutActivity) getActivity();
            DrawerFragment fragment = new DrawerFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("count", ++count);
            fragment.setArguments(bundle);
            myActivity.addSlide(fragment);
        }
    }
}
