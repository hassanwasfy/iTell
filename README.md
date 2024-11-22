[![](https://jitpack.io/v/hassanwasfy/iTell.svg)](https://jitpack.io/#hassanwasfy/iTell) [![License: Personal/Non-Commercial](https://img.shields.io/badge/license-Free%20for%20Personal-orange.svg)](https://github.com/hassanwasfy/iTell#license)
[![License: Commercial](https://img.shields.io/badge/license-Contact%20for%20Commercial-red.svg)](https://github.com/hassanwasfy/iTell#license)



# **ScreenTracker Documentation**

## **Table of Contents**
- [Overview](#overview)
- [Features](#features)
- [How to Use](#how-to-use)
- [Best Practices](#best-practices)
- [FAQ](#faq)
- [License](#license)

---

## **Overview**
`ScreenTracker` is a lightweight utility for tracking which screen (Activity or Fragment) is currently visible in your Android application. It works automatically, leveraging lifecycle callbacks to ensure seamless integration without requiring manual logging in every screen.

---

## **Features**
- **Automatic Screen Tracking:** Automatically monitors and tracks the currently active `Activity` and `Fragment`.
- **Memory Safe:** Uses `WeakReference` to ensure there are no memory leaks.
- **Analytics Integration:** Optional support for Firebase Analytics to log screen views.
- **Lightweight:** Designed for large projects with minimal performance overhead.
- **Simple API:** Provides easy access to the current screen name.
- **Framework-Compatible:** Works with XML and Jetpack Compose-based applications.

---

## **How to Use**

### **1. Gradle Configuration**
Ensure the following dependencies are added in your `build.gradle.kts` file:

```kotlin
dependencies {
    implementation ("com.github.hassanwasfy:iTell:${leatest_version}")
}
```

### **2. Initialize in Application Class**
Add the following code to your `Application` class to start tracking activities and fragments globally:

```kotlin
class MyApplication : Application() {

   override fun onCreate() {
      super.onCreate()

      // Initialize the ScreenTracker
      //remember to add this line ONLY in DEBUG mode
        if (BuildConfig.DEBUG){
            ScreenTrackerInitializer().initialize(this)
        }
   }
}
```

### **3. Retrieve the Current Screen Name**
You can retrieve the name of the currently active screen (Activity or Fragment) using:

```kotlin
val currentScreenName = ScreenTracker.getCurrentScreenName()
Log.d("ScreenTracker", "Current Screen: $currentScreenName")
```

### **4. Optional: Firebase Integration**
If you use Firebase Analytics, initialize it in `ScreenTracker` dynamically (if available) to log screen views:

```kotlin
ScreenTracker.initializeFirebase(context)
```

---

## **Best Practices**
1. **Seamless Integration:** No need to manually invoke `ScreenTracker` in each screen; lifecycle callbacks handle tracking automatically.
2. **Memory Management:** Ensure memory safety using the built-in `WeakReference` implementation.
3. **Analytics Integration:** Use the optional Firebase Analytics integration to log screen views for better user experience monitoring.
4. **Debugging Aid:** Utilize `ScreenTracker` logs to debug navigation issues or monitor screen transitions.
5. **Scope Limitations:** Be mindful of nested fragments or dynamically added fragments. Modify callbacks if necessary to support these.

---

## **FAQ**

### **Q: Does this work with nested fragments?**
Yes, but you may need to extend the implementation to support `childFragmentManager` for nested fragments.

### **Q: Does it work with Compose?**
Yes, it works with Jetpack Compose-based activities and fragments.

### **Q: Is this library memory-intensive?**
No, the use of `WeakReference` ensures that references to activities and fragments do not cause memory leaks.

### **Q: Can I disable Firebase integration?**
Yes, Firebase Analytics is optional and can be skipped entirely if not needed.


## License

This project is licensed under a dual-license model:

1. **Personal/Non-Commercial Use**:
   - Licensed under a free, non-commercial license.
   - For personal and educational use only.
   - Commercial use is prohibited.

2. **Commercial Use**:
   - A commercial license must be purchased.
   - Contact hassanwasfy7@gmail.com for details.

By using this software, you agree to the terms of the appropriate license.

---

## **Conclusion**
`ScreenTracker` is an essential utility for tracking user navigation in Android apps, offering simplicity, efficiency, and flexibility. Whether for debugging or analytics, it seamlessly integrates into your app with minimal setup.

