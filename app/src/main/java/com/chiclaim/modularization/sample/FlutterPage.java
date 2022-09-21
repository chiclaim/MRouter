package com.chiclaim.modularization.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.chiclaim.modularization.R;

/**
 * Created by kumu@2dfire.com on 2022/9/21.
 */
public class FlutterPage extends BaseActivity {
    public static final String FLUTTER_PATH = "FlutterPage";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutter_page_mock);
    }


    public void goNewActivity(View view) {
        Intent intent = new Intent(this, AActivity.class);
        startActivity(intent);
    }
}
