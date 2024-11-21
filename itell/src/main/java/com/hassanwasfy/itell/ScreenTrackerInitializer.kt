package com.hassanwasfy.itell

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class ScreenTrackerInitializer : Application.ActivityLifecycleCallbacks {

    fun initialize(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityResumed(activity: Activity) {
        ScreenTracker.currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        if (ScreenTracker.currentActivity == activity) {
            ScreenTracker.currentActivity = null
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentResumed(fm: FragmentManager, fragment: Fragment) {
                        ScreenTracker.currentFragment = fragment
                    }

                    override fun onFragmentPaused(fm: FragmentManager, fragment: Fragment) {
                        if (ScreenTracker.currentFragment == fragment) {
                            ScreenTracker.currentFragment = null
                        }
                    }
                },
                true
            )
        }
    }

    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}

