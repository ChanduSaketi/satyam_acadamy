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
import com.cmt.services.model.PageNation
import com.cmt.view.activity.PlainActivity

class CategoriesVM : ViewModel() {
    val categoriesData: MutableLiveData<PageNation<*>> = MutableLiveData()

    fun setCategories(view: View, pageCount: String) {

        val activity = view.context as PlainActivity
        activity.activityLoader(true)
        val params: HashMap<String, String> = HashMap()
        params[IConstants.Params.page_no] = pageCount
        HomeAuthenticationApi().allCategories(params, object : RetrofitCallBack {
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
                            categoriesData.value = dataResponse
                        } else {
                            activity.setSnackBar(error)
                        }
                    }
                }
            }

        })


    }


}