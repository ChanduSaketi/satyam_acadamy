package com.cmt.viewModel.fragment

import android.content.Intent
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.R
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.my_doctors.app.AppController
import com.cmt.view.activity.PaymentActivity
import com.cmt.view.activity.PlainActivity

class SubscirbeplansVM : ViewModel() {
    val renewalAmount: MutableLiveData<String> = MutableLiveData()
    fun clickHere(view: View) {
        val activity = view.context as PlainActivity
        val intent = Intent(activity, PaymentActivity::class.java)
//        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.cart)
        AppController.paymentThrough = IConstants.Defaults.subscription
        intent.putExtra(IConstants.IntentStrings.price,renewalAmount.value?:"")
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)
    }
}