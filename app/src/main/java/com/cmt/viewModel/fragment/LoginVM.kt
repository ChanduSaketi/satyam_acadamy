package com.cmt.viewModel.fragment

import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.R
import com.cmt.helper.*
import com.cmt.my_doctors.app.AppController
import com.cmt.services.api.AuthenticationApi
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity

class LoginVM : ViewModel() {
    var usernameError: MutableLiveData<String> = MutableLiveData()
    var pswrdError: MutableLiveData<String> = MutableLiveData()
    var model: MutableLiveData<UserDetailsModel> = MutableLiveData()
    var toggle1: Boolean = true


    init {
        model.value = UserDetailsModel()
    }


/*    fun logIn(view: View){
        view.hideKeyboard()
        val activity = view.context as PlainActivity
        usernameError.postValue(null)
        pswrdError.postValue(null)
        val intent = Intent(activity, MainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.home)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)
        activity.finishAffinity()
    }*/

    fun forgotPassword(view: View) {
        val activity = view.context as PlainActivity
        val intent = Intent(activity, PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.forgotPassword)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)
    }

    fun registerBtn(view: View) {
        view.hideKeyboard()
        val activity = view.context as PlainActivity
        val intent = Intent(activity, PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.register)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)
    }

    fun logIn(view: View) {
        val activity = view.context as PlainActivity
        view.hideKeyboard()

        if (model.value?.name.isNullOrEmpty() && model.value?.password.isNullOrEmpty()) {
            pswrdError.postValue("Please enter userName & password")
            usernameError.postValue(null)
        } else if (model.value?.name.isNullOrEmpty()) {
            usernameError.postValue("Please Enter email / mobile number")
            pswrdError.postValue(null)
        } else if (model.value?.password.isNullOrEmpty()) {
            pswrdError.postValue("Please Enter Password")
            usernameError.postValue(null)
        } else if (model.value?.name?.matches("^[a-zA-Z]*$".toRegex()) == true) {
            if (Patterns.EMAIL_ADDRESS?.matcher(model.value?.name ?: "")
                    ?.matches()!! == false
            ) {
                usernameError.postValue("Please enter valid email")
                pswrdError.postValue(null)

            } else {
                usernameError.postValue(null)
                pswrdError.postValue(null)
                AppController.through = IConstants.Defaults.login
                userLogin(activity)

            }
        } else if (model.value?.name?.matches("^[0-9]*$".toRegex()) == true) {
            if (model.value?.name?.length != 10) {
                usernameError.postValue("Please enter valid mobile")
                pswrdError.postValue(null)

            } else {
                usernameError.postValue(null)
                pswrdError.postValue(null)
                AppController.through = IConstants.Defaults.login
                userLogin(activity)

            }
        } else {
            if (Patterns.EMAIL_ADDRESS?.matcher(model.value?.name ?: "")
                    ?.matches()!! == false
            ) {
                usernameError.postValue("Please enter valid email")
                pswrdError.postValue(null)

            } else {
                usernameError.postValue(null)
                pswrdError.postValue(null)
                userLogin(activity)


            }
        }
    }

    fun userLogin(context: Context) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(activity!!)
        params[IConstants.Params.login_id] = model.value?.name ?: ""
        params[IConstants.Params.password] = model.value?.password ?: ""

        AuthenticationApi().loginApi(params, object : RetrofitCallBack {
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

                            if(dataResponse.token!=null){
                                AppPreferences().setAccessToken(activity,dataResponse.token!!)
                            }

                            if (dataResponse.is_verified == 0) {
                                val intent = Intent(activity, PlainActivity::class.java)
                                intent.putExtra(IConstants.IntentStrings.type,
                                    IConstants.FragmentType.otp)
                                intent.putExtra(IConstants.IntentStrings.payload,
                                    dataResponse.mobile)
                                activity.startActivity(intent)
                                Toast.makeText(activity, "Please verify OTP", Toast.LENGTH_SHORT)
                                    .show()
                                activity.overridePendingTransition(R.anim.enter, R.anim.exit)
                                activity.finishAffinity()
                            } else {
                                AppPreferences().setUserId(activity, dataResponse.id)

                                usernameError.postValue(null)
                                pswrdError.postValue(null)
                                AppController.through = IConstants.Defaults.login
                                val intent = Intent(activity, MainActivity::class.java)
                                intent.putExtra(IConstants.IntentStrings.type,
                                    IConstants.FragmentType.home)
                                AppPreferences().setUserId(activity, dataResponse.id)
                                IConstants.ValueHolder.userData?.name = "Indu"
                                activity.startActivity(intent)
                                activity.overridePendingTransition(R.anim.enter, R.anim.exit)
                                activity.finishAffinity()
                            }

                        } else {
                            activity.setSnackBar(apiResponse.message)
                        }
                    }
                }
            }

        })

    }


}