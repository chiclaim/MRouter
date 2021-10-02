package com.chiclaim.modularization.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.fragment.app.Fragment;
import android.widget.Toast;

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

    public void navigation(Context context) {
        startInActivity(context);
    }

    public void navigation(Activity activity) {
        startInActivity(activity);
    }

    public void navigation(Activity activity, int requestCode) {
        startInActivity(activity, true, requestCode);
    }

    public void navigation(Fragment fragment) {
        startInFragment(fragment);
    }

    public void navigation(Fragment fragment, int requestCode) {
        startInFragment(fragment, true, requestCode);
    }


    public <T> T find() {
        Class clazz = getClassFromRouter(false);
        if (clazz == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (obj instanceof Fragment) {
            Fragment fragment = (Fragment) obj;
            fragment.setArguments(mExtras);
        } else if (obj instanceof android.app.Fragment) {
            android.app.Fragment fragment = (android.app.Fragment) obj;
            fragment.setArguments(mExtras);
        }

        return (T)obj;
    }

    private void startInActivity(Context context) {
        startInActivity(context, false, 0);
    }

    private void startInActivity(Context context, boolean isForResult, int requestCode) {
        Class clazz = getClassFromRouter();
        if (clazz == null) {
            return;
        }
        if (isForResult) {
            ((Activity) context).startActivityForResult(getIntent(context, clazz), requestCode);
        } else {
            context.startActivity(getIntent(context, clazz));
        }

    }

    private void startInFragment(Fragment fragment) {
        startInFragment(fragment, false, 0);
    }

    private void startInFragment(Fragment fragment, boolean isForResult, int requestCode) {
        Class clazz = getClassFromRouter();
        if (clazz == null) {
            return;
        }
        if (isForResult) {
            fragment.startActivityForResult(getIntent(fragment.getActivity(), clazz), requestCode);
        } else {
            fragment.startActivity(getIntent(fragment.getActivity(), clazz));
        }
    }

    private Class getClassFromRouter(boolean showTip) {
        Class clazz = RouteManager.getInstance().getRoute(mPath);
        if (clazz == null && showTip) {
            Toast.makeText(MRouter.getInstance().getContext(), "did not found class by " + mPath, Toast.LENGTH_SHORT).show();
        }
        return clazz;
    }

    private Class getClassFromRouter() {
        return getClassFromRouter(true);
    }


    private Intent getIntent(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        if (mExtras != null) {
            intent.putExtras(mExtras);
        }
        return intent;
    }

}
