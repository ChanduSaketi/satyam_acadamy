package com.cmt.viewModel.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.cmt.R
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.services.api.AuthenticationApi
import com.cmt.services.apiInterface.Authentication
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity
import com.cmt.view.activity.SplashActivity

class SplashVm : ViewModel() {

    fun setZoomForImage(view: View) {
        val sX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.1f, 1f)
        val sY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.1f, 1f)
        val zoomAnimatorSet = AnimatorSet()
        zoomAnimatorSet.playTogether(sX, sY)
        zoomAnimatorSet.interpolator = LinearInterpolator()
        zoomAnimatorSet.duration = 1500
        zoomAnimatorSet.setTarget(view)
        zoomAnimatorSet.start()
        zoomAnimatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                val activity = view.context as SplashActivity
                val authToken = AppPreferences().getAccessToken(activity)
                if (authToken != null) {
                    autoLoginApi(view.context)
                } else {
                    loginScreen(view.context)
                }
/*
                if (AppPreferences().getUserId(activity)
                        .isEmpty()
                ) {
                    val intent = Intent(activity, PlainActivity::class.java)
                    intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.login)
                    activity.startActivity(intent)
                    activity.finishAffinity()
                    activity.overridePendingTransition(R.anim.enter, R.anim.exit)

                } else {

                    val intent = Intent(activity, MainActivity::class.java)
                    intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.home)
                    activity.startActivity(intent)
                    activity.finishAffinity()
                    activity.overridePendingTransition(R.anim.enter, R.anim.exit)

                }*/
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })
    }

    private fun loginScreen(context: Context) {
        val activity = context as SplashActivity
        val intent = Intent(activity, PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.login)
        activity.startActivity(intent)
        activity.finishAffinity()
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)

    }

    private fun autoLoginApi(context: Context) {
        val activity = context as SplashActivity

        val params = getGlobalParams(context)
        params[IConstants.Params.user_id] = AppPreferences().getUserId(context)
        AuthenticationApi().autoLoginApi(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                if (error != null) {

                    loginScreen(activity)
                    Toast.makeText(activity, "$error", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.status == IConstants.Response.valid) {
                        val intent = Intent(context, MainActivity::class.java)
                        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.home)
                        context.startActivity(intent)
                        activity.overridePendingTransition(
                            R.anim.enter,
                            R.anim.exit
                        )
                    } else {
                        AppPreferences().logout(activity)
                        loginScreen(activity)
                        Toast.makeText(activity, "${apiResponse?.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        })
    }
}