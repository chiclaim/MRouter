package com.chiclaim.modularization.router;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.content.Intent.*;

@IntDef(flag = true, value = {
        FLAG_ACTIVITY_MATCH_EXTERNAL,
        FLAG_ACTIVITY_NO_HISTORY,
        FLAG_ACTIVITY_SINGLE_TOP,
        FLAG_ACTIVITY_NEW_TASK,
        FLAG_ACTIVITY_MULTIPLE_TASK,
        FLAG_ACTIVITY_CLEAR_TOP,
        FLAG_ACTIVITY_FORWARD_RESULT,
        FLAG_ACTIVITY_PREVIOUS_IS_TOP,
        FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS,
        FLAG_ACTIVITY_BROUGHT_TO_FRONT,
        FLAG_ACTIVITY_RESET_TASK_IF_NEEDED,
        FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY,
        FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET,
        FLAG_ACTIVITY_NO_USER_ACTION,
        FLAG_ACTIVITY_REORDER_TO_FRONT,
        FLAG_ACTIVITY_NO_ANIMATION,
        FLAG_ACTIVITY_CLEAR_TASK,
        FLAG_ACTIVITY_TASK_ON_HOME,
        FLAG_ACTIVITY_RETAIN_IN_RECENTS,
        FLAG_ACTIVITY_LAUNCH_ADJACENT,
        FLAG_ACTIVITY_REQUIRE_NON_BROWSER,
        FLAG_ACTIVITY_REQUIRE_DEFAULT,
})
@Retention(RetentionPolicy.SOURCE)
public @interface Flags {
}