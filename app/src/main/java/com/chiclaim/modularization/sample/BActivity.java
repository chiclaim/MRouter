package com.chiclaim.modularization.sample;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chiclaim.modularization.sample.BaseActivity;
import com.chiclaim.modularization.R;
import com.chiclaim.modularization.sample.RouterPaths;
import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Route;

/**
 * Description：
 *
 * Created by kumu on 2017/11/16.
 */
@Route(path = RouterPaths.ACTIVITY_B)
public class BActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_layout);
        TextView textView = (TextView) findViewById(R.id.text_name);
        textView.setText("B");
    }

    public void startNewActivity(View view){
//        startActivity(new Intent(this, CActivity.class));
        MRouter.getInstance().build(RouterPaths.ACTIVITY_C).navigate(this);
    }
}
