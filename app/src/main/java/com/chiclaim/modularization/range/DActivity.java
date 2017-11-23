package com.chiclaim.modularization.range;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chiclaim.modularization.BaseActivity;
import com.chiclaim.modularization.R;
import com.chiclaim.modularization.utils.RouterActivityManager;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/11/16.
 */

public class DActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_layout);
        TextView textView = (TextView) findViewById(R.id.text_name);
        textView.setText("D");
        Button button = (Button) findViewById(R.id.btn_start_activity);
        button.setText("关闭区间Activity[B-D]");

        findViewById(R.id.btn_finish_all_activity).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_finish_all_activity_except_activity_class).setVisibility(View.VISIBLE);

        int count = RouterActivityManager.get().getActivityCount();
        textView.append("activity count:");
        textView.append("" + count);
    }

    public void startNewActivity(View view) {
        RouterActivityManager.get().finishAllByRange(BActivity.class, DActivity.class);
    }

    public void finishAll(View view) {
        RouterActivityManager.get().finishAllActivity();
    }

    public void finishAllExceptByClass(View view) {
        RouterActivityManager.get().finishAllActivityExcept(BActivity.class);
    }


}
