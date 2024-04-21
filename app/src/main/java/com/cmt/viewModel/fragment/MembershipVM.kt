package com.cmt.viewModel.fragment

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.R
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.MemberShipApi
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.MembershipModel
import com.cmt.view.activity.PlainActivity
import org.json.JSONObject

class MembershipVM : ViewModel() {
    val model: MutableLiveData<MembershipModel> = MutableLiveData()
    val rootVisibility: MutableLiveData<Boolean> = MutableLiveData()

    init {
        rootVisibility.value = false

    }

    fun purchaseCourse(view: View) {
        val activity = view.context as PlainActivity
        val intent = Intent(activity, PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.categeories)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)


    }

    fun membership(view: View) {
        val activity = view.context as PlainActivity
        activity.activityLoader(true)
        val params = HashMap<String, String>()
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity)

        MemberShipApi().MymemberShipApi(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                rootVisibility.postValue(true)
                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as APIResponse<*>
                        if (apiResponse.status == IConstants.ServerValidate.valid) {
                            val dataResponse = apiResponse.data as MembershipModel
                            model.value = dataResponse
                        }
                    }
                }
            }

        })
    }
}