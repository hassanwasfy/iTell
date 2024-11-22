package com.hassanwasfy.itell

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import java.lang.ref.WeakReference

object ScreenTracker {
    private var currentActivityRef: WeakReference<Activity?> = WeakReference(null)
    private var currentFragmentRef: WeakReference<Fragment?> = WeakReference(null)
    private var firebaseAnalytics: FirebaseAnalytics? = null

    /**
     * Initialize Firebase Analytics dynamically, if available.
     */
    fun initializeFirebase(context: Context) {
        try {
            firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            Log.d("ScreenTracker", "Firebase Analytics initialized")
        } catch (e: Exception) {
            Log.w("ScreenTracker", "Firebase Analytics not available: ${e.message}")
        }
    }

    var currentActivity: Activity?
        get() = currentActivityRef.get()
        set(value) {
            currentActivityRef = WeakReference(value)
        }

    var currentFragment: Fragment?
        get() = currentFragmentRef.get()
        set(value) {
            currentFragmentRef = WeakReference(value)
        }

    /**
     * Get the current screen name.
     */
    fun getCurrentScreenName(): String {
        val activityName = currentActivity?.javaClass?.simpleName
        val fragmentName = currentFragment?.javaClass?.simpleName
        return when {
            fragmentName != null -> "Fragment: $fragmentName in Activity: $activityName"
            activityName != null -> "Activity: $activityName"
            else -> "No active screen"
        }
    }

    /**
     * Log the current screen name to Logcat and Firebase Analytics (if initialized).
     */
    fun logCurrentScreen() {
        val screenName = getCurrentScreenName()
        Log.d("ScreenTracker", "Current Screen: $screenName")

        // Log to Firebase Analytics if available
        firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
    }
}



