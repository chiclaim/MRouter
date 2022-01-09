package com.chiclaim.modularization.sample;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Route;

/**
 * Description：
 *
 * Created by kumu on 2017/7/24.
 */
@Route(path = "/app/NoAutowiredActivity")
public class NoAutowiredActivity extends BaseActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MRouter.getInstance().inject(this);

    }


}
