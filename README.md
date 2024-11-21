
# **ScreenTracker Documentation**

## **Overview**
`ScreenTracker` is a utility to track which screen (Activity or Fragment) is currently visible in your Android application. This solution works globally without requiring manual logging in each screen.

---

## **Features**
- Automatically tracks the currently visible `Activity` and `Fragment`.
- Provides a simple API to get the current screen's name.
- Prevents memory leaks using `WeakReference`.
- Compatible with XML-based applications.

---

## **How to Use ScreenTracker**

### **1. Add the Tracker to Your Project**
Ensure that the `ScreenTracker` and `ScreenTrackerInitializer` classes are included in your project.

#### **ScreenTracker**
Tracks the current activity and fragment:

```kotlin
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

   fun getCurrentScreenName(): String? {
      val activityName = currentActivity?.javaClass?.simpleName
      val fragmentName = currentFragment?.javaClass?.simpleName
      return when {
         fragmentName != null -> "Fragment: $fragmentName in Activity: $activityName"
         activityName != null -> "Activity: $activityName"
         else -> "No active screen"
      }
   }
}
```

#### **ScreenTrackerInitializer**
Sets up lifecycle callbacks to monitor activities and fragments:

```kotlin
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
```

---

### **2. Initialize in Application Class**
Add the following code to your `Application` class to start tracking activities and fragments globally:

```kotlin
class MyApplication : Application() {

   override fun onCreate() {
      super.onCreate()

      // Initialize the ScreenTracker
      ScreenTrackerInitializer().initialize(this)
   }
}
```

---

### **3. Get the Current Screen Name**
You can retrieve the name of the currently active screen (Activity or Fragment) using:

```kotlin
val currentScreenName = ScreenTracker.getCurrentScreenName()
Log.d("ScreenTracker", "Current Screen: $currentScreenName")
```

---

## **Use Cases**

### **Log Current Screen**
Log the current screen in your app for debugging purposes:

```kotlin
Log.d("ScreenTracker", "Currently visible screen: ${ScreenTracker.getCurrentScreenName()}")
```

---

### **Integrate with Analytics**
Send the current screen name to an analytics tool (e.g., Firebase):

```kotlin
fun logScreenToAnalytics() {
   val currentScreenName = ScreenTracker.getCurrentScreenName()
   if (currentScreenName != null) {
      Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
         param(FirebaseAnalytics.Param.SCREEN_NAME, currentScreenName)
      }
   }
}
```

---

### **Debug in Crash Logs**
Include the current screen in crash logs or error reports:

```kotlin
fun logError(error: Throwable) {
   val currentScreenName = ScreenTracker.getCurrentScreenName()
   Log.e("ErrorLogger", "Error on screen: $currentScreenName", error)
}
```

---

## **Best Practices**
1. **Avoid Explicit Calls:** You don’t need to explicitly call `ScreenTracker` in every activity or fragment. The lifecycle callbacks handle this automatically.
2. **Use for Monitoring:** Use `ScreenTracker` to monitor user navigation patterns or troubleshoot issues without intrusive logging.
3. **Memory Safety:** The use of `WeakReference` ensures there are no memory leaks.

---

## **Testing the Implementation**

1. Launch your application.
2. Navigate through different activities and fragments.
3. Check the logs to verify that the current screen is being tracked accurately:

   ```
   D/ScreenTracker: Current Screen: Activity: MainActivity
   D/ScreenTracker: Current Screen: Fragment: HomeFragment in Activity: MainActivity
   ```

---

## **FAQ**

### **Q: Will this work for fragments within fragments (nested fragments)?**
Yes, if you want to support nested fragments, modify the fragment lifecycle callbacks to track fragments inside `childFragmentManager`.

### **Q: Does this solution cause performance overhead?**
No, the solution is lightweight and uses `WeakReference` to avoid memory leaks. The lifecycle callbacks are efficiently managed by the Android framework.
