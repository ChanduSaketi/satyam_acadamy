package com.cmt.viewModel.fragment

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.HomeAuthenticationApi
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.MyOrdersModel
import com.cmt.services.model.PageNation
import com.cmt.view.activity.PlainActivity

class MyOrderVM : ViewModel() {
//    val orderDetails: MutableLiveData<MutableList<MyOrdersModel>> = MutableLiveData()
    val orderDetails: MutableLiveData<PageNation<*>> = MutableLiveData()


    /*  fun setOrdersData() {
          val data = mutableListOf<MyOrdersModel>()
          for (i in 1..5) {
              data.add(MyOrdersModel("569845781", "Feb 25-2022, 8:00 AM"))
              data.add(MyOrdersModel("895784578", "Jan 28-2022, 8:00 AM"))
          }
          orderDetails.postValue(data)
      }*/

    fun setOrders(view: View, pageCount: String) {

        val activity = view.context as PlainActivity
        activity.activityLoader(true)
        val params: HashMap<String, String> = HashMap()
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity)
        params[IConstants.Params.page_no] = pageCount
        HomeAuthenticationApi().myOrders(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as APIResponse<*>
                        if (response.status == IConstants.ServerValidate.valid) {
                            val dataResponse = apiResponse.data as PageNation<*>
                            orderDetails.value = dataResponse
                        } else {
                            activity.setSnackBar(error)
                        }
                    }
                }
            }

        })


    }
}