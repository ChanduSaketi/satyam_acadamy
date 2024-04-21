package com.cmt.viewModel.fragment

import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.R
import com.cmt.databinding.FragmentForgotPasswordBinding
import com.cmt.helper.IConstants
import com.cmt.helper.hideKeyboard
import com.cmt.helper.setSnackBar
import com.cmt.my_doctors.app.AppController
import com.cmt.services.api.AuthenticationApi
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.activity.PlainActivity
import com.cmt.view.fragment.OtpFragment

class ForogotPasswordVM : ViewModel() {
    lateinit var binding: FragmentForgotPasswordBinding
    val userId: MutableLiveData<String> = MutableLiveData()

    fun sendOtp(view: View) {
        view.hideKeyboard()
        val activity = view.context as FragmentActivity
        if (userId.value?.trim().isNullOrEmpty()) {
            binding.userId.setError("Please Enter Your Mobile Number / Email To Get OTP")
        } else if (userId.value?.matches("^[a-zA-Z]*$".toRegex()) == true) {
            if (Patterns.EMAIL_ADDRESS?.matcher(userId.value ?: "")
                    ?.matches()!! == false
            ) {
                binding.userId.setError("Please enter valid email")
            } else {
                binding.userId.setError(null)
                forgotPassword(activity)


                /* AppController.passwordType = IConstants.Defaults.forgot_password
                 IConstants.ValueHolder.loginType = IConstants.Defaults.email
                 val intent = Intent(activity, PlainActivity::class.java)
                 intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.otp)
                 intent.putExtra(IConstants.IntentStrings.payload, userId.value.toString() ?: "")
                 activity.startActivity(intent)
                 activity.overridePendingTransition(R.anim.enter, R.anim.exit)*/

            }
        } else if (userId.value?.matches("^[0-9]*$".toRegex()) == true) {
            if (userId.value?.length != 10) {
                binding.userId.setError("Please enter valid mobile number")
            } else {

                binding.userId.setError(null)
                forgotPassword(activity)

                /* IConstants.ValueHolder.loginType = IConstants.Defaults.mobile
                 AppController.passwordType = IConstants.Defaults.forgot_password
                 val intent = Intent(activity, PlainActivity::class.java)
                 intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.otp)
                 intent.putExtra(IConstants.IntentStrings.payload, userId.value.toString() ?: "")
                 activity.startActivity(intent)
                 activity.overridePendingTransition(R.anim.enter, R.anim.exit)*/

            }
        } else {
            if (Patterns.EMAIL_ADDRESS?.matcher(userId.value ?: "")
                    ?.matches()!! == false
            ) {
                binding.userId.setError("Please enter valid email")
            } else {
                binding.userId.setError(null)
                forgotPassword(activity)

                /*  IConstants.ValueHolder.loginType = IConstants.Defaults.email
                  AppController.passwordType = IConstants.Defaults.forgot_password
                  val intent = Intent(activity, PlainActivity::class.java)
                  intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.otp)
                  intent.putExtra(IConstants.IntentStrings.payload, userId.value.toString() ?: "")
                  activity.startActivity(intent)
                  activity.overridePendingTransition(R.anim.enter, R.anim.exit)*/
            }
        }
    }

    fun forgotPassword(context: Context) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = HashMap<String, String>()
        params[IConstants.Params.login_id] = userId.value ?: ""
        AuthenticationApi().forgotPassword(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                when {
                    error != null -> {
                        activity?.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as APIResponse<*>
                        if (apiResponse.status == IConstants.ServerValidate.valid) {
                            activity?.setSnackBar(apiResponse.message)
                            Toast.makeText(activity, "${apiResponse.message}", Toast.LENGTH_SHORT)
                                .show()
                            activity?.onBackPressed()
                        } else {
                            activity?.setSnackBar(apiResponse.message)
                        }
                    }
                }
            }

        })


    }


}