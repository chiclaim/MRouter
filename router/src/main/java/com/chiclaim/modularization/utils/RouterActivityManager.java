package com.chiclaim.modularization.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chiclaim.modularization.router.RouteManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/11/15.
 */

public class RouterActivityManager {


    private Stack<Activity> stack;


    private static RouterActivityManager instance;


    private RouterActivityManager() {
        stack = new Stack<>();
    }


    public static synchronized RouterActivityManager get() {
        if (instance == null) {
            instance = new RouterActivityManager();
        }
        return instance;
    }


    public void add(Activity activity) {
        stack.add(activity);
    }

    public Activity getActivity() {
        if (stack.size() == 0) {
            return null;
        }
        return stack.lastElement();
    }

    /**
     * 关闭当前界面Activity（栈顶）
     */
    public void finishActivity() {
        if (stack.size() == 0) {
            return;
        }
        Activity activity = stack.lastElement();
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

    public void finishActivity(Class clazz) {
        Iterator<Activity> it = stack.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (null != activity && activity.getClass().equals(clazz)) {
                it.remove();
                activity.finish();
            }
        }
    }

    /**
     * 关闭的Activity界面在其他模块
     *
     * @param routerPath Activity path
     */
    public void finishActivity(String routerPath) {
        Class clazz = RouteManager.getInstance().getRoute(routerPath);
        if (clazz == null) {
            return;
        }
        Iterator<Activity> it = stack.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (null != activity && activity.getClass().equals(clazz)) {
                it.remove();
                activity.finish();
            }
        }
    }

    private List<Class> getClassesInRouter(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        List<Class> classes = new ArrayList<>(list.size());
        for (Object obj : list) {
            if (obj instanceof Activity) {
                classes.add(obj.getClass());
            } else if (obj instanceof Class) {
                classes.add((Class) obj);
            } else if (obj instanceof String) {
                Class clazz = RouteManager.getInstance().getRoute(obj.toString());
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
    public void finishActivity(List list) {
        List<Class> classes = getClassesInRouter(list);
        if (classes == null || classes.isEmpty()) {
            return;
        }
        for (Class clazz : classes) {
            Iterator<Activity> it = stack.iterator();
            while (it.hasNext()) {
                Activity activity = it.next();
                if (null != activity && activity.getClass().equals(clazz)) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }


    /**
     * 关闭所有界面
     */
    public void finishAllActivity() {
        Iterator<Activity> it = stack.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (null != activity) {
                it.remove();
                activity.finish();
            }
        }
    }


    /**
     * 关闭所有的Activity除了excepts
     *
     * @param excepts 需要保留的Activity 里面的元素可以是Activity、Activity的Class、Activity的routerPath
     */
    public void finishAllActivityExcept(List excepts) {
        List<Class> classes = getClassesInRouter(excepts);
        if (classes == null || classes.isEmpty()) {
            return;
        }
        Iterator<Activity> it = stack.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (null == activity) {
                it.remove();
                continue;
            }
            boolean closeable = true;
            for (Class clazz : classes) {
                if (activity.getClass().equals(clazz)) {
                    closeable = false;
                }
            }
            if (closeable) {
                it.remove();
                activity.finish();
            }
        }
    }

    public void finishAllActivityExcept(String routerPath) {
        Class clazz = RouteManager.getInstance().getRoute(routerPath);
        if (clazz == null) {
            return;
        }
        Iterator<Activity> it = stack.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (null != activity && !activity.getClass().equals(clazz)) {
                it.remove();
                activity.finish();
            }
        }
    }

    public void finishAllActivityExcept(Class activityClass) {
        if (activityClass == null) {
            return;
        }
        Iterator<Activity> it = stack.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (null != activity && !activity.getClass().equals(activityClass)) {
                it.remove();
                activity.finish();
            }
        }
    }

    /**
     * 关闭区间所有界面，包含begin和end。如栈中有A、B、C、D、E、F，想关闭C到F之间的Activity，begin参数就是C，end参数就是F
     *
     * @param begin
     * @param end
     */
    public void finishAllByRange(Class begin, Class end) {
        //判断start和end是否都在stack中
        Iterator<Activity> it = stack.iterator();
        Activity beginActivity = null, endActivity = null;
        while (it.hasNext()) {
            Activity activity = it.next();
            if (activity == null) {
                it.remove();
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
            Iterator<Activity> iterator = stack.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                if (closable) {
                    activity.finish();
                    iterator.remove();
                    continue;
                }
                if (activity == beginActivity) {
                    closable = true;
                    beginActivity.finish();
                    iterator.remove();
                } else if (activity == endActivity) {
                    endActivity.finish();
                    iterator.remove();
                    break;
                }
            }
        } else if (beginActivity != null) {
            beginActivity.finish();
        } else if (endActivity != null) {
            endActivity.finish();
        }

    }

    /**
     * 关闭区间所有界面，包含begin和endRouterPath
     *
     * @param begin         Activity的Class
     * @param endRouterPath Activity的routerPath
     * @see RouterActivityManager#finishAllByRange(Class, Class)
     */
    public void finishAllByRange(Class begin, String endRouterPath) {
        Class endClazz = RouteManager.getInstance().getRoute(endRouterPath);
        if (endClazz == null) {
            return;
        }
        finishAllByRange(begin, endClazz);
    }

    public void finishAllByRange(String beginRouterPath, Class endClass) {
        Class beginClass = RouteManager.getInstance().getRoute(beginRouterPath);
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
     * @see RouterActivityManager#finishAllByRange(Class, Class)
     */
    public void finishAllByRange(String beginRouterPath, String endRouterPath) {
        Class beginClazz = RouteManager.getInstance().getRoute(beginRouterPath);
        if (beginClazz == null) {
            return;
        }

        Class endClazz = RouteManager.getInstance().getRoute(endRouterPath);
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
    public void finishAllStartTo(Context context, Class targetClass, Bundle extras) {
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
        Class targetClazz = RouteManager.getInstance().getRoute(targetRouterPath);
        if (targetClazz == null) {
            return;
        }
        finishAllStartTo(context, targetClazz, extras);
    }


    /**
     * 获取栈中Activity数量
     */
    public int getActivityCount() {
        if (stack != null) {
            return stack.size();
        }
        return 0;
    }


}

