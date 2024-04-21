package com.cmt.viewModel.fragment

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.R
import com.cmt.helper.AppPreferences
import com.cmt.helper.ICallback
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.my_doctors.app.AppController
import com.cmt.services.api.BooksVideosApi
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.CheckOutModel
import com.cmt.services.model.HomeBannerModel
import com.cmt.view.activity.PaymentActivity
import com.cmt.view.activity.PlainActivity
import com.cmt.view.fragment.ClearCartDialogFragment

class CartVM : ViewModel() {
    val deleteBoolean: MutableLiveData<Boolean> = MutableLiveData()
    val isAlreadyPurchased: MutableLiveData<Boolean> = MutableLiveData()
    val checkOutModel: MutableLiveData<CheckOutModel> = MutableLiveData()
    val newUserSubscriptionAmount = "0"

    init {
        deleteBoolean.value = true
        isAlreadyPurchased.value = false
    }

    fun removeItem(view: View) {
        val activity = view.context as FragmentActivity
        AppController.dialogType = IConstants.Defaults.clear_cart
        val dialog = ClearCartDialogFragment(object : ICallback {
            override fun delegate(any: Any?) {
                if (any != null) {
                    deleteBoolean.value = false
                }
            }

        })
        dialog.show(activity.supportFragmentManager, null)
    }

    fun continueBtn(view: View) {
        val activity = view.context as FragmentActivity
        val intent = Intent(activity, PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.purchased_successful)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)
        activity.finish()

    }

    fun checkOut(view: View, model: CheckOutModel) {
        checkOutModel.value = model
    }

    fun placeOrder(view: View) {
        val activity = view.context as PlainActivity
        val intent = Intent(activity, PaymentActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.payload, checkOutModel.value)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)
    }

}