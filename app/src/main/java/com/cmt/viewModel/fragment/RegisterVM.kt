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
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.hideKeyboard
import com.cmt.helper.setSnackBar
import com.cmt.my_doctors.app.AppController
import com.cmt.services.api.AuthenticationApi
import com.cmt.services.apiInterface.Authentication
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.activity.PlainActivity

class RegisterVM : ViewModel() {
    var registerModel: MutableLiveData<UserDetailsModel> = MutableLiveData()

    init {
        registerModel.value = UserDetailsModel()
    }

    fun login(view: View) {
        val activity = view.context as FragmentActivity
        activity.onBackPressed()
    }

/*
    fun register(view: View) {
        view.hideKeyboard()
        AppController.passwordType = IConstants.Defaults.registration
        val activity = view.context as FragmentActivity
        val intent = Intent(activity, PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.otp)
        intent.putExtra(IConstants.IntentStrings.payload, registerModel.value?.phone_number ?: "")
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)
    }
*/


    //validations
    fun register(view: View) {
        view.hideKeyboard()
        val activity = view.context as FragmentActivity

        when {
            registerModel.value?.name.isNullOrEmpty() && registerModel.value?.email.isNullOrEmpty() && registerModel.value?.mobile.isNullOrEmpty()
                    && registerModel.value?.password.isNullOrEmpty() -> {
                Toast.makeText(
                    activity,
                    "All the fields are required to register",
                    Toast.LENGTH_SHORT
                ).show()
            }
            registerModel.value?.name.isNullOrEmpty() -> {
                Toast.makeText(activity, "Please enter name", Toast.LENGTH_SHORT).show()
            }
            registerModel.value?.email.isNullOrEmpty() -> {
                Toast.makeText(activity, "Please enter your Email", Toast.LENGTH_SHORT).show()
            }
            !Patterns.EMAIL_ADDRESS?.matcher(registerModel.value?.email ?: "")?.matches()!! -> {
                Toast.makeText(activity, "Please enter valid Email", Toast.LENGTH_SHORT).show()
            }
            registerModel.value?.mobile.isNullOrEmpty() -> {
                Toast.makeText(activity, "Please enter your mobile number", Toast.LENGTH_SHORT)
                    .show()
            }
            registerModel.value?.mobile?.length != 10 -> {
                Toast.makeText(activity, "Please enter valid mobile number", Toast.LENGTH_SHORT)
                    .show()
            }
            registerModel.value?.password.isNullOrEmpty() -> {
                Toast.makeText(activity, "Please choose your password", Toast.LENGTH_SHORT).show()
            }
            registerModel.value?.password?.length!! < 6 -> {
                Toast.makeText(activity, "Password Should be 6 characters", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                userRegister(activity)

            }
        }

    }

    private fun userRegister(context: Context) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(activity!!)
        params[IConstants.Params.name] = registerModel.value?.name ?: ""
        params[IConstants.Params.email] = registerModel.value?.email ?: ""
        params[IConstants.Params.mobile] = registerModel.value?.mobile ?: ""
        params[IConstants.Params.password] = registerModel.value?.password ?: ""

        AuthenticationApi().registerApi(params, object : RetrofitCallBack {
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
                            AppController.passwordType = IConstants.Defaults.registration
                            AppController.through = IConstants.Defaults.registration
                            IConstants.ValueHolder.userData = registerModel.value
                            val intent = Intent(activity, PlainActivity::class.java)
                            intent.putExtra(IConstants.IntentStrings.type,
                                IConstants.FragmentType.otp)
                            intent.putExtra(IConstants.IntentStrings.payload, dataResponse.mobile)
                            activity.startActivity(intent)
                            activity.overridePendingTransition(R.anim.enter, R.anim.exit)

                        } else {
                            activity.setSnackBar(apiResponse.message)
                        }
                    }
                }
            }

        })


    }
}