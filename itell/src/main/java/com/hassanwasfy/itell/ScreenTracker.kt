package com.hassanwasfy.itell

import android.app.Activity
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

object ScreenTracker {
    private var currentActivityRef: WeakReference<Activity?> = WeakReference(null)
    private var currentFragmentRef: WeakReference<Fragment?> = WeakReference(null)

    var currentActivity: Activity?
        get() = currentActivityRef.get()
        internal set(value) {
            currentActivityRef = WeakReference(value)
        }

    var currentFragment: Fragment?
        get() = currentFragmentRef.get()
        internal set(value) {
            currentFragmentRef = WeakReference(value)
        }

    fun getCurrentScreenName(): String {
        val activityName = currentActivity?.javaClass?.simpleName
        val fragmentName = currentFragment?.javaClass?.simpleName
        return when {
            fragmentName != null -> "Fragment: $fragmentName in Activity: $activityName"
            activityName != null -> "Activity: $activityName"
            else -> "No active screen"
        }
    }
}

