package com.chiclaim.modularization.router;

import android.app.Application;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/24.
 */

public class MRouter {

    private static MRouter instance;



    public static synchronized MRouter getInstance() {
        if (instance == null) {
            instance = new MRouter();
        }
        return instance;
    }

    public void init(Application application){

    }

    public Router build(String path) {
        return new Router(path);
    }



}
