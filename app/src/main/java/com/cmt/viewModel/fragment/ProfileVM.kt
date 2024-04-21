package com.cmt.viewModel.fragment

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.R
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.Profile
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.activity.PlainActivity
import java.io.File

class ProfileVM : ViewModel() {
    val model: MutableLiveData<UserDetailsModel> = MutableLiveData()
    val dummyImage: MutableLiveData<String> = MutableLiveData()

    init {
        model.value = UserDetailsModel()
        dummyImage.value = (R.drawable.man).toString()
    }

    fun setData(context: Context) {
        val activity = context as PlainActivity
        activity.activityLoader(true)

        val params = HashMap<String, String>()
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity) ?: ""



        Profile().showProfile(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                val apiResponse = response as APIResponse<*>
                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
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

    /*fun setDummyData() {
        model.value?.name = "Indu"
        model.value?.email = "indu@colourmoon.com"
        model.value?.mobile = "9876543210"

    }*/
}