package com.chiclaim.modularization.range;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chiclaim.modularization.BaseActivity;
import com.chiclaim.modularization.R;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/11/16.
 */

public class BActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_layout);
        TextView textView = (TextView) findViewById(R.id.text_name);
        textView.setText("B");
    }

    public void startNewActivity(View view){
        startActivity(new Intent(this, CActivity.class));
    }
}
