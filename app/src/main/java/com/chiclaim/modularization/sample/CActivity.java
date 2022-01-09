package com.chiclaim.modularization.sample;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chiclaim.modularization.R;
import com.chiclaim.modularization.business.RouterPaths;
import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Route;

/**
 * Description：
 *
 * Created by kumu on 2017/11/16.
 */
@Route(path = RouterPaths.ACTIVITY_C)
public class CActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_layout);
        TextView textView = (TextView) findViewById(R.id.text_name);
        textView.setText("C");
    }

    public void startNewActivity(View view){
//        startActivity(new Intent(this, DActivity.class));
        MRouter.getInstance().build(RouterPaths.ACTIVITY_D).navigate(this);
    }
}
