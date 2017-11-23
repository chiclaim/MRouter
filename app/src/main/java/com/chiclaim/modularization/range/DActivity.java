package com.chiclaim.modularization.range;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chiclaim.modularization.BaseActivity;
import com.chiclaim.modularization.R;
import com.chiclaim.modularization.RouterPaths;
import com.chiclaim.modularization.router.annotation.Route;
import com.chiclaim.modularization.utils.RouterActivityManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/11/16.
 */
@Route(path = RouterPaths.Activity_D)
public class DActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_layout);
        TextView textView = (TextView) findViewById(R.id.text_name);
        textView.setText("D");
        Button button = (Button) findViewById(R.id.btn_start_activity);
        button.setText("关闭区间Activity[B-D]");

        findViewById(R.id.btn_finish_top_activity).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_finish_activity_by_class).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_finish_activity_by_router_path).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_finish_all_activity).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_finish_all_activity_except_activity_class).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_finish_all_activity_except_router_path).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_finish_all_activity_except_by_list).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_finish_all_activity_by_list).setVisibility(View.VISIBLE);

        int count = RouterActivityManager.get().getActivityCount();
        textView.append("\n\nactivity count:");
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

    public void finishAllExceptByRouterPath(View view) {
        RouterActivityManager.get().finishAllActivityExcept(RouterPaths.Activity_B);
    }

    public void finishAllByList(View view) {
        List<Object> list = new ArrayList<>();
        list.add(CActivity.class);
        list.add(RouterPaths.Activity_D);
        RouterActivityManager.get().finishActivity(list);
    }

    public void finishActivityByClass(View view) {
        RouterActivityManager.get().finishActivity(getClass());
    }

    public void finishActivityByPath(View view) {
        RouterActivityManager.get().finishActivity(RouterPaths.Activity_D);
    }

    public void finishTopActivity(View view) {
        RouterActivityManager.get().finishActivity();
    }

    /**
     * 保留A和B Activity
     *
     * @param view
     */
    public void finishAllExceptByList(View view) {
        List<Object> list = new ArrayList<>();
        list.add(AActivity.class);
        list.add(RouterPaths.Activity_B);
        RouterActivityManager.get().finishAllActivityExcept(list);
    }


}
