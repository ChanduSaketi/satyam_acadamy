package com.cmt.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.cmt.databinding.ActivitySplashBinding
import com.cmt.helper.AppPreferences
import com.cmt.viewModel.activity.SplashVm
import com.google.firebase.messaging.FirebaseMessaging

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binder: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivitySplashBinding.inflate(layoutInflater).apply {
            viewModel = ViewModelProvider(this@SplashActivity).get(SplashVm::class.java)
            lifecycleOwner = this@SplashActivity
        }
        setContentView(binder.root)

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isComplete) {
                val fbToken = it.result.toString()
                Log.d("FireBaseToken", "Token: $fbToken")
                AppPreferences().setPushNotificationToken(applicationContext, fbToken)
            }
        }
        binder.viewModel?.setZoomForImage(binder.splashImage)
    }
}