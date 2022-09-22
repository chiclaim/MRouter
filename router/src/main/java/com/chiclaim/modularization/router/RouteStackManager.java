package com.chiclaim.modularization.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 用于跨组件的路由页面栈管理。可以灵活的销毁 Activity 栈的任意区间的 Activity。
 *
 * <p></p>
 * 在 MRouter.getInstance().init(context) 会注册 registerActivityLifecycleCallbacks,
 * 使用者不需要在 BaseActivity 中单独处理。
 *
 * <p></p>
 * Created by kumu on 2017/11/15.
 *
 * <p></p>
 * Edited on 2021/12/26.
 */

public class RouteStackManager {

    private static final String FLUTTER_PATH = "flutter_path_identity";


    private final CopyOnWriteArrayList<Activity> stack;


    private static volatile RouteStackManager instance;


    private RouteStackManager() {
        stack = new CopyOnWriteArrayList<>();
    }


    public static RouteStackManager get() {
        if (instance == null) {
            synchronized (RouteStackManager.class) {
                if (instance == null) {
                    instance = new RouteStackManager();
                }
            }
        }
        return instance;
    }


    /**
     * 自动在 Activity.onCreate 调用
     */
    void add(@NonNull Activity activity) {
        if (stack.contains(activity)) return;
        stack.add(activity);
    }

    /**
     * 自动在 Activity.onDestroy 调用
     */
    void remove(@NonNull Activity activity) {
        if (!stack.contains(activity)) return;
        stack.remove(activity);
    }

    /**
     * 获取当前界面Activity（栈顶）
     *
     * @return Activity
     */
    public Activity getActivity() {
        if (stack.size() == 0) {
            return null;
        }
        return stack.get(stack.size() - 1);
    }

    /**
     * 关闭当前界面Activity（栈顶）
     */
    public void finishActivity() {
        if (stack.size() == 0) {
            return;
        }
        Activity activity = stack.get(stack.size() - 1);
        activity.finish();
    }

    /**
     * 关闭特定的Activity界面
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            stack.remove(activity);
            activity.finish();
        }
    }

    public void finishActivity(Class<?> clazz) {
        for (Activity activity : stack) {
            if (null != activity && activity.getClass().equals(clazz)) {
                stack.remove(activity);
                activity.finish();
            }
        }
    }

    /**
     * 关闭的Activity界面在其他模块
     *
     * @param routerPath Activity router or Flutter router
     */
    public void finishActivity(String routerPath) {
        Class<?> clazz = RouteManager.getInstance().getRoute(routerPath);
        // 原生路由
        if (clazz != null) {
            finishActivity(clazz);
            return;
        }

        // 是否为 Flutter 路由
        for (Activity activity : stack) {
            if (activity == null) continue;
            if (matchFlutterActivity(activity, routerPath)) {
                stack.remove(activity);
                activity.finish();
                return;
            }
        }
    }

    private List<Class<?>> getClassesInRouter(List<?> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        List<Class<?>> classes = new ArrayList<>(list.size());
        for (Object obj : list) {
            if (obj instanceof Activity) {
                classes.add(obj.getClass());
            } else if (obj instanceof Class) {
                classes.add((Class<?>) obj);
            } else if (obj instanceof String) {
                Class<?> clazz = RouteManager.getInstance().getRoute(obj.toString());
                if (clazz != null) {
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }


    /**
     * @param list 里面的元素可以是Activity、Activity的Class、Activity的routerPath
     */
    public void finishActivity(List<?> list) {
        List<Class<?>> classes = getClassesInRouter(list);
        if (classes == null || classes.isEmpty()) {
            return;
        }
        for (Class<?> clazz : classes) {
            finishActivity(clazz);
        }
    }


    /**
     * 关闭所有界面
     */
    public void finishAllActivity() {
        for (Activity activity : stack) {
            if (null != activity) {
                stack.remove(activity);
                activity.finish();
            }
        }

    }


    /**
     * 关闭所有的Activity除了excepts
     *
     * @param excepts 需要保留的Activity 里面的元素可以是Activity、Activity的Class、Activity的routerPath
     */
    public void finishAllActivityExcept(List<?> excepts) {
        List<Class<?>> classes = getClassesInRouter(excepts);
        if (classes == null || classes.isEmpty()) {
            finishAllActivity();
            return;
        }
        for (Activity activity : stack) {
            if (null == activity) {
                stack.remove(null);
                continue;
            }
            boolean closeable = true;
            for (Class<?> clazz : classes) {
                if (activity.getClass().equals(clazz)) {
                    closeable = false;
                }
            }
            if (closeable) {
                stack.remove(activity);
                activity.finish();
            }
        }
    }

    public void finishAllActivityExcept(String routerPath) {
        Class<?> clazz = RouteManager.getInstance().getRoute(routerPath);
        finishAllActivityExcept(clazz);
    }

    public void finishAllActivityExcept(Class<?> activityClass) {
        if (activityClass == null) {
            finishAllActivity();
            return;
        }
        for (Activity activity : stack) {
            if (null != activity && !activity.getClass().equals(activityClass)) {
                stack.remove(activity);
                activity.finish();
            }

        }
    }

    public void finishAllActivityExcept(Activity activity) {
        if (activity == null) {
            finishAllActivity();
            return;
        }
        finishAllActivityExcept(activity.getClass());
    }

    /**
     * 关闭区间所有界面，包含begin和end。如栈中有A、B、C、D、E、F，想关闭C到F之间的Activity，begin参数就是C，end参数就是F
     *
     * @param begin
     * @param end
     */
    public void finishAllByRange(Class<?> begin, Class<?> end) {
        //判断 start 和 end 是否都在 stack 中
        Activity beginActivity = null, endActivity = null;

        for (Activity activity : stack) {
            if (activity == null) {
                stack.remove(null);
                continue;
            }
            if (activity.getClass().equals(begin)) {
                beginActivity = activity;
            } else if (activity.getClass().equals(end)) {
                endActivity = activity;
            }
        }

        boolean closable = false;
        if (beginActivity != null && endActivity != null) {
            for (Activity activity : stack) {
                if (closable) {
                    stack.remove(activity);
                    activity.finish();
                    continue;
                }
                if (activity == beginActivity) {
                    closable = true;
                    stack.remove(activity);
                    beginActivity.finish();
                } else if (activity == endActivity) {
                    stack.remove(activity);
                    endActivity.finish();
                    break;
                }
            }
        } else if (beginActivity != null) {
            stack.remove(beginActivity);
            beginActivity.finish();
        } else if (endActivity != null) {
            stack.remove(endActivity);
            endActivity.finish();
        }

    }

    /**
     * 关闭区间所有界面，包含begin和endRouterPath
     *
     * @param begin         Activity的Class
     * @param endRouterPath Activity的routerPath
     * @see RouteStackManager#finishAllByRange(Class, Class)
     */
    public void finishAllByRange(Class<?> begin, String endRouterPath) {
        Class<?> endClazz = RouteManager.getInstance().getRoute(endRouterPath);
        if (endClazz == null) {
            return;
        }
        finishAllByRange(begin, endClazz);
    }

    public void finishAllByRange(String beginRouterPath, Class<?> endClass) {
        Class<?> beginClass = RouteManager.getInstance().getRoute(beginRouterPath);
        if (beginClass == null) {
            return;
        }
        finishAllByRange(beginClass, endClass);
    }

    /**
     * 关闭区间所有界面，包含beginRouterPath和endRouterPath
     *
     * @param beginRouterPath Activity的routerPath
     * @param endRouterPath   Activity的routerPath
     * @see RouteStackManager#finishAllByRange(Class, Class)
     */
    public void finishAllByRange(String beginRouterPath, String endRouterPath) {
        Class<?> beginClazz = RouteManager.getInstance().getRoute(beginRouterPath);
        if (beginClazz == null) {
            return;
        }

        Class<?> endClazz = RouteManager.getInstance().getRoute(endRouterPath);
        if (endClazz == null) {
            return;
        }
        finishAllByRange(beginClazz, endClazz);
    }

    /**
     * 关闭所有界面，然后跳转到某个界面，支持参数自动注入
     *
     * @param context
     * @param targetClass 需要跳转的目标界面
     * @param extras      传递参数给目标界面
     */
    public void finishAllStartTo(Context context, Class<?> targetClass, Bundle extras) {
        finishAllActivity();
        if (targetClass == null) {
            return;
        }

        Intent intent = new Intent(context, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    /**
     * 关闭所有界面，然后跳转到某个界面，支持参数自动注入
     *
     * @param context
     * @param targetRouterPath 需要跳转的目标界面
     * @param extras           传递参数给目标界面
     */
    public void finishAllStartTo(Context context, String targetRouterPath, Bundle extras) {
        Class<?> targetClazz = RouteManager.getInstance().getRoute(targetRouterPath);
        if (targetClazz == null) {
            return;
        }
        finishAllStartTo(context, targetClazz, extras);
    }

    private boolean matchFlutterActivity(@NonNull Activity activity, @NonNull String path) {
        Intent intent = activity.getIntent();
        return intent != null && path.equals(intent.getStringExtra(FLUTTER_PATH));
    }


    /**
     * 关闭栈顶到 path 对应的 Activity。不包含 path 对应的页面
     *
     * @param path 可以是原生路由，也可以是 flutter 路由
     */
    public void finishUntil(@NonNull String path) {
        Class<?> targetClazz = RouteManager.getInstance().getRoute(path);
        boolean canDelete = false;
        for (Activity activity : stack) {
            if (canDelete) {
                if (activity != null) {
                    activity.finish();
                }
                stack.remove(activity);
                continue;
            }
            // 原生路由
            if (targetClazz != null && targetClazz == activity.getClass()) {
                canDelete = true;
            }
            // flutter 路由
            else if (matchFlutterActivity(activity, path)) {
                canDelete = true;
            }
        }

    }

    public void finishUntil(@NonNull Class<? extends Activity> targetActivity) {
        boolean canDelete = false;
        for (Activity activity : stack) {
            if (canDelete) {
                if (activity != null) {
                    activity.finish();
                }
                stack.remove(activity);
                continue;
            }
            canDelete = targetActivity.equals(activity.getClass());
        }
    }


    /**
     * 获取栈中Activity数量
     */
    public int getActivityCount() {
        return stack.size();
    }


}

