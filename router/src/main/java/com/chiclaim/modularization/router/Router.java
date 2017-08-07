package com.chiclaim.modularization.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Description
 * <br/>
 * Created by kumu on 2017/7/24.
 */

public class Router {

    private String mPath;
    private Bundle mExtras;

    Router(String path) {
        mPath = path;
    }

    private void checkBundleNull() {
        if (mExtras == null) {
            mExtras = new Bundle();
        }
    }


    public Router putBoolean(String key, boolean value) {
        checkBundleNull();
        mExtras.putBoolean(key, value);
        return this;
    }

    public Router putBooleanArray(String key, boolean[] value) {
        checkBundleNull();
        mExtras.putBooleanArray(key, value);
        return this;
    }


    public Router putByte(String key, byte value) {
        checkBundleNull();
        mExtras.putByte(key, value);
        return this;
    }

    public Router putByteArray(String key, byte[] value) {
        checkBundleNull();
        mExtras.putByteArray(key, value);
        return this;
    }


    public Router putChar(String key, char value) {
        checkBundleNull();
        mExtras.putChar(key, value);
        return this;
    }

    public Router putCharArray(String key, char[] value) {
        checkBundleNull();
        mExtras.putCharArray(key, value);
        return this;
    }


    public Router putShort(String key, short value) {
        checkBundleNull();
        mExtras.putShort(key, value);
        return this;
    }

    public Router putShortArray(String key, short[] values) {
        checkBundleNull();
        mExtras.putShortArray(key, values);
        return this;
    }


    public Router putFloat(String key, float value) {
        checkBundleNull();
        mExtras.putFloat(key, value);
        return this;
    }

    public Router putFloatArray(String key, float[] value) {
        checkBundleNull();
        mExtras.putFloatArray(key, value);
        return this;
    }


    public Router putInt(String key, int value) {
        checkBundleNull();
        mExtras.putInt(key, value);
        return this;
    }

    public Router putIntArray(String key, int[] value) {
        checkBundleNull();
        mExtras.putIntArray(key, value);
        return this;
    }

    public Router putIntList(String key, ArrayList<Integer> value) {
        checkBundleNull();
        mExtras.putIntegerArrayList(key, value);
        return this;
    }

    public Router putDouble(String key, double value) {
        checkBundleNull();
        mExtras.putDouble(key, value);
        return this;
    }

    public Router putDoubleArray(String key, double[] value) {
        checkBundleNull();
        mExtras.putDoubleArray(key, value);
        return this;
    }

    public Router putLong(String key, long value) {
        checkBundleNull();
        mExtras.putLong(key, value);
        return this;
    }

    public Router putLongArray(String key, long[] value) {
        checkBundleNull();
        mExtras.putLongArray(key, value);
        return this;
    }

    public Router putString(String key, String value) {
        checkBundleNull();
        mExtras.putString(key, value);
        return this;
    }

    public Router putStringArray(String key, String[] values) {
        checkBundleNull();
        mExtras.putStringArray(key, values);
        return this;
    }

    public Router putStringList(String key, ArrayList<String> values) {
        checkBundleNull();
        mExtras.putStringArrayList(key, values);
        return this;
    }


    public Router putParcelable(String key, Parcelable value) {
        checkBundleNull();
        mExtras.putParcelable(key, value);
        return this;
    }

    public Router putParcelableArray(String key, Parcelable[] value) {
        checkBundleNull();
        mExtras.putParcelableArray(key, value);
        return this;
    }

    public Router putParcelableList(String key, ArrayList<? extends Parcelable> value) {
        checkBundleNull();
        mExtras.putParcelableArrayList(key, value);
        return this;
    }

    public Router putSerializable(String key, Serializable value) {
        checkBundleNull();
        mExtras.putSerializable(key, value);
        return this;
    }

    public Router putExtras(Bundle extras) {
        if (extras != null) {
            checkBundleNull();
            mExtras.putAll(extras);
        }
        return this;
    }

    public void navigation(Activity activity) {
        Class clazz = RouteManager.getInstance().getRoute(mPath);
        if (clazz == null) {
            return;
        }
        activity.startActivity(getIntent(activity, clazz));
    }

    public void navigation(Activity activity, int requestCode) {
        Class clazz = RouteManager.getInstance().getRoute(mPath);
        if (clazz == null) {
            return;
        }
        activity.startActivityForResult(getIntent(activity, clazz), requestCode);
    }

    public void navigation(Fragment fragment) {
        Class clazz = RouteManager.getInstance().getRoute(mPath);
        if (clazz == null) {
            return;
        }
        fragment.startActivity(getIntent(fragment.getActivity(), clazz));
    }

    public void navigation(Fragment fragment, int requestCode) {
        Class clazz = RouteManager.getInstance().getRoute(mPath);
        if (clazz == null) {
            return;
        }
        fragment.startActivityForResult(getIntent(fragment.getActivity(), clazz), requestCode);
    }

    private Intent getIntent(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        if (mExtras != null) {
            intent.putExtras(mExtras);
        }
        return intent;
    }

}
