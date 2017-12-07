package com.chiclaim.modularization.range;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chiclaim.modularization.BaseActivity;
import com.chiclaim.modularization.R;
import com.chiclaim.modularization.RouterPaths;
import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Route;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/11/16.
 */
@Route(path = RouterPaths.ACTIVITY_A)
public class AActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_layout);
        TextView textView = (TextView) findViewById(R.id.text_name);
        textView.setText("A");
    }

    public void startNewActivity(View view) {
        //startActivity(new Intent(this, BActivity.class));
        MRouter.getInstance().build(RouterPaths.ACTIVITY_B).navigation(this);
    }
}
