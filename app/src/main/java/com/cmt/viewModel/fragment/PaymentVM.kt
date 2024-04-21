package com.cmt.viewModel.fragment

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.R
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.my_doctors.app.AppController
import com.cmt.services.api.PaymentGateWay
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.CheckOutModel
import com.cmt.view.activity.PaymentActivity
import com.cmt.view.activity.PlainActivity
import com.razorpay.PaymentData
import org.json.JSONObject

class PaymentVM : ViewModel() {
    val paymentObject: MutableLiveData<JSONObject> = MutableLiveData()
    var payment_id: String? = null


    fun paymentProcess(view: View, model: CheckOutModel? = null, price: String?= null) {
        val activity = view.context as PaymentActivity
        val params = HashMap<String, String>()
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity)

        if (AppController.paymentThrough == IConstants.Defaults.subscription) {
            params[IConstants.Params.price] = price ?: ""
            params[IConstants.Params.category_id] = ""
        } else {
            params[IConstants.Params.price] = model?.price ?: ""
            params[IConstants.Params.category_id] = model?.category_id ?: ""
        }

        PaymentGateWay().paymentMethod(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as JSONObject
                        if (apiResponse.getString(IConstants.ServerValidate.status) == IConstants.ServerValidate.valid) {
                            paymentObject.value =
                                apiResponse.getJSONObject(IConstants.ServerValidate.data)

                            //payment_id =


                        } else {
                            Toast.makeText(activity,
                                apiResponse.getString(IConstants.ServerValidate.message),
                                Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }

        })
    }

    fun paymentSuccess(context: Context, p1: PaymentData?) {
        val activity = context as PaymentActivity
        val params = HashMap<String, String>()
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity)
        params[IConstants.Params.razorpay_order_id] = p1!!.orderId
        params[IConstants.Params.razorpay_payment_id] = p1.paymentId
//            paymentObject.value?.getJSONObject("meta_info")?.getString("payment_id") ?: ""
        params[IConstants.Params.razorpay_signature] = p1.signature
        PaymentGateWay().paymentOrderSuccessMethod(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as APIResponse<*>
                        if (apiResponse.status == IConstants.ServerValidate.invalid) {
                            activity.setSnackBar(apiResponse.message)
                        } else {
                            activity.setSnackBar(apiResponse.message)
                            Toast.makeText(activity, "${apiResponse.message}", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(activity, PlainActivity::class.java)
                            intent.putExtra(IConstants.IntentStrings.type,
                                IConstants.FragmentType.purchased_successful)
                            activity.startActivity(intent)
                            activity.overridePendingTransition(R.anim.enter, R.anim.exit)
                            activity.finishAffinity()
                        }
                    }
                }
            }

        })

    }

    fun paymentFailure(context: Context, p2: PaymentData?) {
        val activity = context as PaymentActivity

        val params = HashMap<String, String>()
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity)
//       params[IConstants.Params.razorpay_payment_id] =p2?.paymentId?:""
        params[IConstants.Params.razorpay_payment_id] =
            paymentObject.value?.getString("payment_id") ?: ""
//                params[IConstants.Params.razorpay_order_id] = p2?.orderId ?: ""
        params[IConstants.Params.razorpay_order_id] =
            paymentObject.value?.getJSONObject("meta_info")?.getString("order_id") ?: ""
        PaymentGateWay().paymentOrderFailureMethod(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as APIResponse<*>
                        if (apiResponse.status == IConstants.ServerValidate.invalid) {
                            activity.setSnackBar(apiResponse.message)
                            activity.onBackPressed()
                        } else {
                            activity.setSnackBar(apiResponse.message)
                            Toast.makeText(activity, "${apiResponse.message}", Toast.LENGTH_SHORT)
                                .show()

                        }
                    }
                }
            }

        })
    }
}