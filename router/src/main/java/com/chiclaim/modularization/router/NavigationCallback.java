package com.chiclaim.modularization.router;

public interface NavigationCallback {

    /**
     * No router found
     */
    void onMiss();

    void onSuccess();

}
