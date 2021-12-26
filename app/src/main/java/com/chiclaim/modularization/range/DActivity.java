package com.chiclaim.modularization.range;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chiclaim.modularization.BaseActivity;
import com.chiclaim.modularization.R;
import com.chiclaim.modularization.RouterPaths;
import com.chiclaim.modularization.TargetActivity;
import com.chiclaim.modularization.router.annotation.Route;
import com.chiclaim.modularization.router.RouterActivityManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 *
 * Created by kumu on 2017/11/16.
 */
@Route(path = RouterPaths.ACTIVITY_D)
public class DActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_layout);
        TextView textView = (TextView) findViewById(R.id.text_name);
        textView.setText("D");
        findViewById(R.id.btn_start_activity).setVisibility(View.GONE);

        findViewById(R.id.btn_finish_range_by_class).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_finish_range_begin_router_path1).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_finish_range_begin_router_path2).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_finish_range_begin_router_path3).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_finish_all_start_to).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_finish_all_start_to_by_router_path).setVisibility(View.VISIBLE);

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

    public void finishRange(View view) {
        RouterActivityManager.get().finishAllByRange(BActivity.class, DActivity.class);
    }

    public void finishRangeBeginRouterPath1(View view) {
        RouterActivityManager.get().finishAllByRange(RouterPaths.ACTIVITY_B, DActivity.class);
    }

    public void finishRangeBeginRouterPath2(View view) {
        RouterActivityManager.get().finishAllByRange(BActivity.class, RouterPaths.ACTIVITY_D);
    }

    public void finishRangeBeginRouterPath3(View view) {
        RouterActivityManager.get().finishAllByRange(RouterPaths.ACTIVITY_B, RouterPaths.ACTIVITY_D);
    }

    public void finishAll(View view) {
        RouterActivityManager.get().finishAllActivity();
    }

    public void finishAllExceptByClass(View view) {
        RouterActivityManager.get().finishAllActivityExcept(BActivity.class);
    }

    public void finishAllExceptByRouterPath(View view) {
        RouterActivityManager.get().finishAllActivityExcept(RouterPaths.ACTIVITY_B);
    }

    public void finishAllByList(View view) {
        List<Object> list = new ArrayList<>();
        list.add(CActivity.class);
        list.add(RouterPaths.ACTIVITY_D);
        RouterActivityManager.get().finishActivity(list);
    }

    public void finishActivityByClass(View view) {
        RouterActivityManager.get().finishActivity(getClass());
    }

    public void finishActivityByPath(View view) {
        RouterActivityManager.get().finishActivity(RouterPaths.ACTIVITY_D);
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
        list.add(RouterPaths.ACTIVITY_B);
        RouterActivityManager.get().finishAllActivityExcept(list);
    }

    public void finishAllStartToByClass(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("name", "chiclain");
        bundle.putString("gender", "male");
        bundle.putString("address", "幸福大道210");
        RouterActivityManager.get().finishAllStartTo(this, TargetActivity.class, bundle);
    }

    public void finishAllStartToByRouterPath(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("name", "chiclain");
        bundle.putString("gender", "male");
        bundle.putString("address", "幸福大道210");
        RouterActivityManager.get().finishAllStartTo(this, RouterPaths.ACTIVITY_TARGET, bundle);
    }


}
