package com.chiclaim.modularization.sample;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.chiclaim.modularization.R;
import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;

/**
 * Description：
 *
 * Created by kumu on 2017/7/24.
 */
@Route(path = RouterPaths.ACTIVITY_TARGET)
public class TargetActivity extends BaseActivity {

    @Autowired(name = "name")
    String name;
    @Autowired(name = "gender")
    String gender;
    @Autowired(name = "address")
    String address;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_layout);

        MRouter.getInstance().inject(this);

        TextView text = (TextView) findViewById(R.id.text_parameters);
        if (!TextUtils.isEmpty(name)) {

            text.setText("自动注入的参数：\nname = ");
            text.append(name);

            text.append("\ngender = ");
            text.append(gender);

            text.append("\naddress = ");
            text.append(address);

        }

    }
}
