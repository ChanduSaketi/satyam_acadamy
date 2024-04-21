package com.cmt.viewModel.activity

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.HomeAuthenticationApi
import com.cmt.services.api.Profile
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.activity.MainActivity

class MainActivityViewModel(application: Application) : BaseViewModel(application) {
    val model: MutableLiveData<UserDetailsModel> = MutableLiveData()

    /*init {
        model.value = UserDetailsModel()
    }*/

    fun setData(context: Context) {
        val activity = context as MainActivity
        activity.activityLoader(true)

        val params = HashMap<String, String>()
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity) ?: ""

        Profile().showProfile(params, object : RetrofitCallBack {
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
                            model.value = dataResponse
                        } else {
                            activity.setSnackBar(apiResponse.message)
                        }

                    }
                }

            }

        })

    }
    fun deviceId(context: Context) {
        val activity = context as MainActivity
        activity.activityLoader(true)
        val params = HashMap<String,String>()

        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity)
        params[IConstants.Params.device_id] = AppPreferences().getPushNotificationToken(activity).toString()
        HomeAuthenticationApi().deviceIdApi(params,object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as APIResponse<*>

                        if (apiResponse.status == IConstants.ServerValidate.valid) {

                        }else{
                            activity.setSnackBar(apiResponse.message)

                        }
                    }
                }
            }

        })
    }



}