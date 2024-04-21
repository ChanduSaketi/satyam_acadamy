package com.cmt.my_doctors.app

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.view.WindowManager

class AppController() : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        setupActivityListener()
    }

    companion object {
        var instance: AppController? = null
        var through: String ?= null
        var passwordType: String? = null
        var dialogType: String? = null
        var paymentThrough: String? = null
        /*  var appointmentType :String? =null
          var formType : String ? =null*/

    }

    private fun setupActivityListener() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }


}