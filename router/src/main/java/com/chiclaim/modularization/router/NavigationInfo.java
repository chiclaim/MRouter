package com.chiclaim.modularization.router;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NavigationInfo {
    @NonNull
    public String routePath;
    @Nullable
    public Bundle parameters;
    public int requestCode;

    public NavigationInfo(@NonNull String routePath, @Nullable Bundle parameters, int requestCode) {
        this.routePath = routePath;
        this.parameters = parameters;
        this.requestCode = requestCode;
    }
}
