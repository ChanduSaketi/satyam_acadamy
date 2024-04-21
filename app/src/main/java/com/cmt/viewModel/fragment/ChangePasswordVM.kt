package com.cmt.viewModel.fragment

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.hideKeyboard
import com.cmt.helper.setSnackBar
import com.cmt.services.api.AuthenticationApi
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.activity.PlainActivity

class ChangePasswordVM : ViewModel() {
    var changePasswordModel: MutableLiveData<UserDetailsModel> = MutableLiveData()
    val newPswrdError: MutableLiveData<String> = MutableLiveData()
    val confirmPswrdError: MutableLiveData<String> = MutableLiveData()
    val oldPswrdError: MutableLiveData<String> = MutableLiveData()

    init {
        changePasswordModel.value = UserDetailsModel()
    }

    fun updatePassword(view: View) {
        view.hideKeyboard()
        val activity = view.context as PlainActivity
        when {
            changePasswordModel.value?.password.isNullOrEmpty() -> {
                oldPswrdError.postValue("Please enter current using password")
                newPswrdError.postValue(null)
                confirmPswrdError.postValue(null)
            }
            changePasswordModel.value?.new_password.isNullOrEmpty() -> {
                oldPswrdError.postValue(null)
                confirmPswrdError.postValue(null)
                newPswrdError.postValue("Please enter new passsword")

            }
            changePasswordModel.value?.new_password?.length!! < 6 -> {
                oldPswrdError.postValue(null)
                confirmPswrdError.postValue(null)
                newPswrdError.postValue("password should be more than 6 characters")

            }
            changePasswordModel.value?.confirm_password.isNullOrEmpty() -> {
                oldPswrdError.postValue(null)
                newPswrdError.postValue(null)
                confirmPswrdError.postValue("Please confirm your passsword")
            }
            changePasswordModel.value?.new_password != changePasswordModel.value?.confirm_password -> {
                oldPswrdError.postValue(null)
                newPswrdError.postValue(null)
                confirmPswrdError.postValue("confirm password should be same as new password")
            }
            else -> {
                oldPswrdError.postValue(null)
                newPswrdError.postValue(null)
                confirmPswrdError.postValue(null)
                changePassword(activity)

            }
        }
    }

    fun changePassword(context: Context) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)

        val params = HashMap<String, String>()
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity!!)
        params[IConstants.Params.old_password] = changePasswordModel.value?.password ?: ""
        params[IConstants.Params.new_password] = changePasswordModel.value?.new_password ?: ""
        params[IConstants.Params.confirm_password] = changePasswordModel.value?.confirm_password ?: ""

        AuthenticationApi().changePasswordApi(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as APIResponse<*>
                        if (apiResponse.status == IConstants.ServerValidate.valid) {
                            Toast.makeText(activity, "${apiResponse.message}", Toast.LENGTH_SHORT)
                                .show()
                            activity.onBackPressed()
//                            activity.setSnackBar(apiResponse.message)
                            activity.finish()

                        } else {
                            activity.setSnackBar(apiResponse.message)
                        }
                    }
                }

            }

        })
    }
}