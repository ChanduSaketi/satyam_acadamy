package com.cmt.viewModel.fragment

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.R
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.BooksVideosApi
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.HomeBannerModel
import com.cmt.view.activity.PlainActivity

class BooksVM : ViewModel() {
    val pdfData: MutableLiveData<MutableList<HomeBannerModel>> = MutableLiveData()
    val upcomingBooks: MutableLiveData<MutableList<HomeBannerModel>> = MutableLiveData()
    val categoryModel: MutableLiveData<HomeBannerModel> = MutableLiveData()
    val rootVisibility: MutableLiveData<Boolean> = MutableLiveData()

    init {
        rootVisibility.value = false
    }

    fun setPdfData(view: View, model: HomeBannerModel) {
        categoryModel.value = model
        val activity = view.context as PlainActivity
        activity.activityLoader(true)
        val params = getGlobalParams(activity)
        params[IConstants.Params.category_id] = categoryModel.value?.id ?: ""
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity)

        BooksVideosApi().sampleBooks(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                rootVisibility.postValue(true)
                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as APIResponse<*>
                        if (response.status == IConstants.ServerValidate.valid) {
                            val dataResponse =
                                (apiResponse.data as MutableList<*>).filterIsInstance<HomeBannerModel>()
                                    .toMutableList()
                            pdfData.value = dataResponse

                        }
                    }
                }
            }

        })

        /*val data = mutableListOf<HomeBannerModel>()
        for (i in 1..3) {
            data.add(
                HomeBannerModel(
                    R.drawable.pdf_icon,
                    "English Practice Book",
                    "Sample Version"
                )
            )
        }
        pdfData.postValue(data)*/
    }

    fun setUpcomingBooks(view: View, model: HomeBannerModel) {
        categoryModel.value = model
        val activity = view.context as PlainActivity
        activity.activityLoader(true)
        val params = getGlobalParams(activity)
        params[IConstants.Params.category_id] = categoryModel.value?.id ?: ""

        BooksVideosApi().upComingBooks(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                rootVisibility.postValue(true)

                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as APIResponse<*>
                        if (response.status == IConstants.ServerValidate.valid) {
                            val dataResponse =
                                (apiResponse.data as MutableList<*>).filterIsInstance<HomeBannerModel>()
                                    .toMutableList()
                            upcomingBooks.value = dataResponse
                        }
                    }
                }
            }

        })
    }


}