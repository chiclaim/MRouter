package com.chiclaim.modularization.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Description
 * <p>
 * Created by kumu on 2017/7/24.
 */

public final class Router {

    private static final int NO_ACTIVITY_RESULT_CODE = -1;

    private String path;
    private Uri uri;
    private final Handler handler;
    private Bundle extras;
    private int enterAnim;
    private int exitAnim;
    private int flags;

    private NavigationCallback callback;

    private Router() {
        this.handler = new Handler(Looper.getMainLooper());
    }


    Router(String path) {
        this();
        this.path = path;
    }

    Router(Uri uri) {
        this();
        this.uri = uri;
    }

    private void checkBundleNull() {
        if (extras == null) {
            extras = new Bundle();
        }
    }


    public Router putBoolean(@Nullable String key, boolean value) {
        checkBundleNull();
        extras.putBoolean(key, value);
        return this;
    }

    public Router putBooleanArray(@Nullable String key, boolean[] value) {
        checkBundleNull();
        extras.putBooleanArray(key, value);
        return this;
    }


    public Router putByte(@Nullable String key, byte value) {
        checkBundleNull();
        extras.putByte(key, value);
        return this;
    }

    public Router putByteArray(@Nullable String key, byte[] value) {
        checkBundleNull();
        extras.putByteArray(key, value);
        return this;
    }


    public Router putChar(@Nullable String key, char value) {
        checkBundleNull();
        extras.putChar(key, value);
        return this;
    }

    public Router putCharArray(@Nullable String key, char[] value) {
        checkBundleNull();
        extras.putCharArray(key, value);
        return this;
    }


    public Router putShort(@Nullable String key, short value) {
        checkBundleNull();
        extras.putShort(key, value);
        return this;
    }

    public Router putShortArray(@Nullable String key, short[] value) {
        checkBundleNull();
        extras.putShortArray(key, value);
        return this;
    }


    public Router putFloat(@Nullable String key, float value) {
        checkBundleNull();
        extras.putFloat(key, value);
        return this;
    }

    public Router putFloatArray(@Nullable String key, float[] value) {
        checkBundleNull();
        extras.putFloatArray(key, value);
        return this;
    }


    public Router putInt(@Nullable String key, int value) {
        checkBundleNull();
        extras.putInt(key, value);
        return this;
    }

    public Router putIntArray(@Nullable String key, int[] value) {
        checkBundleNull();
        extras.putIntArray(key, value);
        return this;
    }

    public Router putIntList(@Nullable String key, ArrayList<Integer> value) {
        checkBundleNull();
        extras.putIntegerArrayList(key, value);
        return this;
    }

    public Router putDouble(@Nullable String key, double value) {
        checkBundleNull();
        extras.putDouble(key, value);
        return this;
    }

    public Router putDoubleArray(@Nullable String key, double[] value) {
        checkBundleNull();
        extras.putDoubleArray(key, value);
        return this;
    }

    public Router putLong(@Nullable String key, long value) {
        checkBundleNull();
        extras.putLong(key, value);
        return this;
    }

    public Router putLongArray(@Nullable String key, long[] value) {
        checkBundleNull();
        extras.putLongArray(key, value);
        return this;
    }

    public Router putString(@Nullable String key, String value) {
        checkBundleNull();
        extras.putString(key, value);
        return this;
    }

    public Router putStringArray(@Nullable String key, String[] value) {
        checkBundleNull();
        extras.putStringArray(key, value);
        return this;
    }

    public Router putStringList(@Nullable String key, ArrayList<String> value) {
        checkBundleNull();
        extras.putStringArrayList(key, value);
        return this;
    }


    public Router putParcelable(@Nullable String key, Parcelable value) {
        checkBundleNull();
        extras.putParcelable(key, value);
        return this;
    }

    public Router putParcelableArray(@Nullable String key, Parcelable[] value) {
        checkBundleNull();
        extras.putParcelableArray(key, value);
        return this;
    }

    public Router putParcelableList(@Nullable String key, ArrayList<? extends Parcelable> value) {
        checkBundleNull();
        extras.putParcelableArrayList(key, value);
        return this;
    }

    public Router putSerializable(@Nullable String key, Serializable value) {
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

    public Router navigateCallback(NavigationCallback callback) {
        this.callback = callback;
        return this;
    }

    public void navigate(Context context) {
        _startActivity(context);
    }

    public void navigate(Activity activity) {
        _startActivity(activity);
    }

    public void navigate(Activity activity, int requestCode) {
        _startActivity(activity, true, requestCode);
    }

    public void navigate(Fragment fragment) {
        startInFragment(fragment);
    }

    public void navigate(Fragment fragment, int requestCode) {
        startInFragment(fragment, true, requestCode);
    }


    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T find() {
        Class<?> clazz = getClassByRouter();
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

    private void _startActivity(Context context) {
        _startActivity(context, false, NO_ACTIVITY_RESULT_CODE);
    }

    private void _startActivity(final Context context, final boolean isForResult, final int requestCode) {
        if (processInterceptors(context, requestCode)) return;
        final Class<?> targetClass = getClassByRouter();
        if (targetClass == null) {
            if (callback != null) callback.onMiss();
            return;
        }
        final Intent intent = createIntent(context, targetClass);

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (context == null) return;
                if (isForResult) {
                    ((Activity) context).startActivityForResult(intent, requestCode);
                } else {
                    if (!(context instanceof Activity))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                if (callback != null) callback.onSuccess();
                applyActivityTransition(context);
            }
        });
    }

    private void startInFragment(@NonNull Fragment fragment) {
        startInFragment(fragment, false, NO_ACTIVITY_RESULT_CODE);
    }

    private void startInFragment(@NonNull final Fragment fragment, final boolean isForResult, final int requestCode) {
        if (processInterceptors(fragment.getActivity(), requestCode)) return;
        final Class<?> targetClass = getClassByRouter();
        if (targetClass == null) {
            if (callback != null) callback.onMiss();
            return;
        }
        final Intent intent = createIntent(fragment.getActivity(), targetClass);

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isForResult) {
                    fragment.startActivityForResult(intent, requestCode);
                } else {
                    fragment.startActivity(intent);
                }
                if (callback != null) callback.onSuccess();
            }
        });
    }

    private Class<?> getClassByRouter() {
        if (getPath() == null)
            throw new IllegalArgumentException("The 'path' parameter is null!, you must invoke navigation(path) first");
        return RouteManager.getInstance().getRoute(getPath());
    }

    private Intent createIntent(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        if (flags != 0) {
            intent.setFlags(flags);
        }
        return intent;
    }


    public Router overridePendingTransition(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        return this;
    }

    private void applyActivityTransition(Context context) {
        if (!(context instanceof Activity)) return;
        if (enterAnim != 0 || exitAnim != 0) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    public Router addFlags(@Flags int flags) {
        this.flags |= flags;
        return this;
    }

    public void removeFlags(@Flags int flags) {
        this.flags &= ~flags;
    }

    public Router setFlags(@Flags int flags) {
        this.flags = flags;
        return this;
    }

    private String getPath() {
        if (uri == null) return path;
        return uri.getPath();
    }


    boolean processInterceptors(Context context, int requestCode) {
        final RouteOption option = MRouter.getInstance().option;
        if (option != null && option.interceptors != null) {
            final String route;
            if (uri != null) {
                route = uri.toString();
            } else {
                route = path;
            }
            for (NavigationInterceptor interceptor : option.interceptors) {
                if (interceptor.onIntercept(context, new NavigationInfo(route, extras, requestCode))) {
                    return true;
                }
            }
        }
        return false;
    }

}