package com.hassanwasfy.itell

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class ScreenTrackerInitializer : Application.ActivityLifecycleCallbacks {

    private val fragmentLifecycleCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentResumed(fm: FragmentManager, fragment: Fragment) {
            ScreenTracker.currentFragment = fragment
            ScreenTracker.logCurrentScreen()
        }
    }

    /**
     * Initialize activity and fragment tracking.
     */
    fun initialize(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
        }
    }

    override fun onActivityResumed(activity: Activity) {
        ScreenTracker.currentActivity = activity
        ScreenTracker.logCurrentScreen()
    }

    // Other lifecycle methods are not required but need to be overridden
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}