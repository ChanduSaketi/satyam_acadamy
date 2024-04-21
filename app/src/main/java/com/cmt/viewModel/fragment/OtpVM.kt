package com.cmt.viewModel.fragment

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.R
import com.cmt.databinding.FragmentOtpBinding
import com.cmt.helper.*
import com.cmt.services.api.AuthenticationApi
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.GenericTextWatcher
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity

class OtpVM : ViewModel() {
    lateinit var binding: FragmentOtpBinding

    var select: MutableLiveData<Boolean> = MutableLiveData()
    var otpTimerString: MutableLiveData<String> = MutableLiveData()
    var errorMsg: MutableLiveData<String> = MutableLiveData()
    var mobileNumber: MutableLiveData<String> = MutableLiveData()

    init {
        select.value = false
    }

    fun setDataToView(mobile: String) {
        mobileNumber.value = mobile
        otpTimerForResend()
        binding.etOtp1.addTextChangedListener(
            GenericTextWatcher(
                etCurrent = binding.etOtp1,
                binding.etOtp2,
                binding.etOtp1
            )
        )
        binding.etOtp2.addTextChangedListener(
            GenericTextWatcher(
                etCurrent = binding.etOtp2,
                binding.etOtp3,
                binding.etOtp1
            )
        )
        binding.etOtp3.addTextChangedListener(
            GenericTextWatcher(
                etCurrent = binding.etOtp3,
                binding.etOtp4,
                binding.etOtp2
            )
        )
        binding.etOtp4.addTextChangedListener(
            GenericTextWatcher(
                etCurrent = binding.etOtp4,
                binding.etOtp4,
                binding.etOtp3
            )
        )
    }

    private fun otpTimerForResend() {
        otpTimerString.postValue(null)
        select.value = false
        val timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished / 1000
                if (second >= 10) {
                    otpTimerString.postValue("00:$second")
                } else if (second < 10 && second > 0) {
                    otpTimerString.postValue("00:0$second")
                } else if (second < 1) {
                    select.value = true
                }
            }

            override fun onFinish() {
                otpTimerString.postValue(null)
                cancel()
            }
        }
        timer.start()
    }

    fun resendOtp(view: View) {
        binding.root.hideKeyboard()
        otpTimerForResend()
        resentOtp(view.context)
    }

    fun verifyOtp(context: Context) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(activity!!)
        params[IConstants.Params.mobile] = mobileNumber.value ?: ""
        params[IConstants.Params.verification_code] =
            binding.etOtp1.text.toString() + binding.etOtp2.text.toString() + binding.etOtp3.text.toString() + binding.etOtp4.text.toString()

        AuthenticationApi().otpApi(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as APIResponse<*>
                        if (apiResponse.status == IConstants.ServerValidate.valid) {

                            val dataResponse = apiResponse.data as UserDetailsModel
                          /*  if (dataResponse.token != null) {
                                AppPreferences().setAccessToken(activity,
                                    dataResponse.token!!)
                            }*/
                            AppPreferences().setUserId(activity, dataResponse.id)
                            AppPreferences().setAccessToken(activity,
                                dataResponse.token!!)
                            
                            val intent = Intent(activity, MainActivity::class.java)
                            intent.putExtra(
                                IConstants.IntentStrings.type,
                                IConstants.FragmentType.home
                            )
                            activity.startActivity(intent)
                            activity.overridePendingTransition(R.anim.enter, R.anim.exit)
                            activity.finishAffinity()

                        } else {
                            activity.setSnackBar(apiResponse.message)
                        }
                    }

                }
            }
        })

    }

    fun resentOtp(context: Context) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(activity!!)
        params[IConstants.Params.mobile] = mobileNumber.value ?: ""

        AuthenticationApi().resendOtpApi(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as APIResponse<*>
                        if (apiResponse.status == IConstants.ServerValidate.valid) {

                            val dataResponse = apiResponse.data as? UserDetailsModel
                            Toast.makeText(activity, "${apiResponse.message}", Toast.LENGTH_SHORT)
                                .show()

                        } else {
                            activity.setSnackBar(apiResponse.message)
                        }
                    }

                }
            }
        })

    }

}
