package com.cmt.viewModel.fragment

import android.content.Intent
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.R
import com.cmt.helper.IConstants
import com.cmt.my_doctors.app.AppController
import com.cmt.view.activity.MainActivity

class SuccessfulVM : ViewModel() {
    val successMessage: MutableLiveData<String> = MutableLiveData()

    fun setData(){
        if(AppController.paymentThrough == IConstants.Defaults.subscription){
            successMessage.value = "Your purchase has been successfuly completed."
        }else{
            successMessage.value = "Your Subscription has been successfuly renewed."
        }
    }
    fun continueBtn(view: View) {
        val activity = view.context as FragmentActivity
        val intent = Intent(activity, MainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.home)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)
        activity.finish()
    }

}