package com.cmt.viewModel.activity

import android.app.Activity
import android.content.Intent
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

class PlainViewModel : ViewModel() {
    var activityTitle: MutableLiveData<String> = MutableLiveData()
    var model: MutableLiveData<UserDetailsModel> = MutableLiveData()

    init {
        model.value = UserDetailsModel()
    }

    fun backEvent(view: View) {
        (view.context as Activity).onBackPressed()
    }

    fun showProfile(view: View) {
        val activity = view.context as PlainActivity
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
                            val intent = Intent(activity, PlainActivity::class.java)
                            intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.editProfile)
                            intent.putExtra(IConstants.IntentStrings.payload,model.value)
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