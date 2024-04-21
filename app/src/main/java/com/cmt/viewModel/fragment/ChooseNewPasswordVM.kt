package com.cmt.viewModel.fragment

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.R
import com.cmt.helper.IConstants
import com.cmt.helper.hideKeyboard
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.activity.PlainActivity

class ChooseNewPasswordVM : ViewModel() {
    val model: MutableLiveData<UserDetailsModel> = MutableLiveData()
    val newPswrdError: MutableLiveData<String> = MutableLiveData()
    val confirmPswrdError: MutableLiveData<String> = MutableLiveData()

    init {
        model.value = UserDetailsModel()
    }

    fun update(view: View) {
        view.hideKeyboard()
        val activity = view.context as FragmentActivity
        when {
            model.value?.new_password.isNullOrEmpty() -> {
                confirmPswrdError.postValue(null)
                newPswrdError.postValue("Please enter new passsword")

            }
            model.value?.new_password?.length!! < 6 -> {
                confirmPswrdError.postValue(null)
                newPswrdError.postValue("password should be more than 6 characters")

            }
            model.value?.confirm_password.isNullOrEmpty() -> {
                newPswrdError.postValue(null)
                confirmPswrdError.postValue("Please confirm your passsword")
            }
            model.value?.new_password != model.value?.confirm_password -> {
                newPswrdError.postValue(null)
                confirmPswrdError.postValue("confirm password should be same as new password")
            }
            else -> {
                newPswrdError.postValue(null)
                confirmPswrdError.postValue(null)

                Toast.makeText(activity,
                    "Created new password succuessfully..,Login with your new password ",
                    Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(activity, PlainActivity::class.java)
                intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.login)
                activity.startActivity(intent)
                activity.overridePendingTransition(R.anim.enter, R.anim.exit)
                activity.finishAffinity()

            }
        }
    }
}

