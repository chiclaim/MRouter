package com.chiclaim.modularization;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Route;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/24.
 */
@Route(path = "xxx/b")
public class NoAutowiredActivity extends BaseActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MRouter.getInstance().inject(this);

    }
}
