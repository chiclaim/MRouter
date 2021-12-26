package com.chiclaim.modularization.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Description
 * <p>
 * Created by kumu on 2017/7/24.
 */

public class Router {

    private final String path;
    private final Handler handler;
    private Bundle extras;
    private int enterAnim;
    private int exitAnim;


    Router(String path) {
        this.path = path;
        this.handler = new Handler(Looper.getMainLooper());
    }

    private void checkBundleNull() {
        if (extras == null) {
            extras = new Bundle();
        }
    }


    public Router putBoolean(String key, boolean value) {
        checkBundleNull();
        extras.putBoolean(key, value);
        return this;
    }

    public Router putBooleanArray(String key, boolean[] value) {
        checkBundleNull();
        extras.putBooleanArray(key, value);
        return this;
    }


    public Router putByte(String key, byte value) {
        checkBundleNull();
        extras.putByte(key, value);
        return this;
    }

    public Router putByteArray(String key, byte[] value) {
        checkBundleNull();
        extras.putByteArray(key, value);
        return this;
    }


    public Router putChar(String key, char value) {
        checkBundleNull();
        extras.putChar(key, value);
        return this;
    }

    public Router putCharArray(String key, char[] value) {
        checkBundleNull();
        extras.putCharArray(key, value);
        return this;
    }


    public Router putShort(String key, short value) {
        checkBundleNull();
        extras.putShort(key, value);
        return this;
    }

    public Router putShortArray(String key, short[] values) {
        checkBundleNull();
        extras.putShortArray(key, values);
        return this;
    }


    public Router putFloat(String key, float value) {
        checkBundleNull();
        extras.putFloat(key, value);
        return this;
    }

    public Router putFloatArray(String key, float[] value) {
        checkBundleNull();
        extras.putFloatArray(key, value);
        return this;
    }


    public Router putInt(String key, int value) {
        checkBundleNull();
        extras.putInt(key, value);
        return this;
    }

    public Router putIntArray(String key, int[] value) {
        checkBundleNull();
        extras.putIntArray(key, value);
        return this;
    }

    public Router putIntList(String key, ArrayList<Integer> value) {
        checkBundleNull();
        extras.putIntegerArrayList(key, value);
        return this;
    }

    public Router putDouble(String key, double value) {
        checkBundleNull();
        extras.putDouble(key, value);
        return this;
    }

    public Router putDoubleArray(String key, double[] value) {
        checkBundleNull();
        extras.putDoubleArray(key, value);
        return this;
    }

    public Router putLong(String key, long value) {
        checkBundleNull();
        extras.putLong(key, value);
        return this;
    }

    public Router putLongArray(String key, long[] value) {
        checkBundleNull();
        extras.putLongArray(key, value);
        return this;
    }

    public Router putString(String key, String value) {
        checkBundleNull();
        extras.putString(key, value);
        return this;
    }

    public Router putStringArray(String key, String[] values) {
        checkBundleNull();
        extras.putStringArray(key, values);
        return this;
    }

    public Router putStringList(String key, ArrayList<String> values) {
        checkBundleNull();
        extras.putStringArrayList(key, values);
        return this;
    }


    public Router putParcelable(String key, Parcelable value) {
        checkBundleNull();
        extras.putParcelable(key, value);
        return this;
    }

    public Router putParcelableArray(String key, Parcelable[] value) {
        checkBundleNull();
        extras.putParcelableArray(key, value);
        return this;
    }

    public Router putParcelableList(String key, ArrayList<? extends Parcelable> value) {
        checkBundleNull();
        extras.putParcelableArrayList(key, value);
        return this;
    }

    public Router putSerializable(String key, Serializable value) {
        checkBundleNull();
        extras.putSerializable(key, value);
        return this;
    }

    public Router putExtras(Bundle extras) {
        if (extras != null) {
            checkBundleNull();
            this.extras.putAll(extras);
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


    @SuppressWarnings("unchecked")
    public <T> T find() {
        Class<?> clazz = getClassFromRouter(false);
        if (clazz == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (obj instanceof Fragment) {
            Fragment fragment = (Fragment) obj;
            fragment.setArguments(extras);
        } else if (obj instanceof android.app.Fragment) {
            android.app.Fragment fragment = (android.app.Fragment) obj;
            fragment.setArguments(extras);
        }

        return (T) obj;
    }

    private void startInActivity(Context context) {
        startInActivity(context, false, 0);
    }

    private void startInActivity(final Context context, final boolean isForResult, final int requestCode) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Class<?> clazz = getClassFromRouter();
                if (clazz == null) return;
                if (context == null) return;
                if (isForResult) {
                    ((Activity) context).startActivityForResult(getIntent(context, clazz), requestCode);
                } else {
                    context.startActivity(getIntent(context, clazz));
                }
                applyTransition(context);
            }
        });
    }

    private void startInFragment(Fragment fragment) {
        startInFragment(fragment, false, 0);
    }

    private void startInFragment(final Fragment fragment, final boolean isForResult, final int requestCode) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Class<?> clazz = getClassFromRouter();
                if (clazz == null) return;
                if (fragment == null) return;
                if (isForResult) {
                    fragment.startActivityForResult(getIntent(fragment.getActivity(), clazz), requestCode);
                } else {
                    fragment.startActivity(getIntent(fragment.getActivity(), clazz));
                }
            }
        });
    }

    private Class<?> getClassFromRouter(boolean showTip) {
        Class<?> clazz = RouteManager.getInstance().getRoute(path);
        if (clazz == null && showTip) {
            Toast.makeText(MRouter.getInstance().getContext(), "did not found class by " + path, Toast.LENGTH_SHORT).show();
        }
        return clazz;
    }

    private Class<?> getClassFromRouter() {
        return getClassFromRouter(true);
    }


    private Intent getIntent(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        return intent;
    }


    public Router overridePendingTransition(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        return this;
    }

    private void applyTransition(Context context) {
        if (!(context instanceof Activity)) return;
        if (enterAnim != 0 || exitAnim != 0) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

}
