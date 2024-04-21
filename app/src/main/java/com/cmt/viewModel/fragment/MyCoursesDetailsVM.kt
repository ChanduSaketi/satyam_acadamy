package com.cmt.viewModel.fragment

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.BooksVideosApi
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.HomeBannerModel
import com.cmt.services.model.VideoModel
import com.cmt.view.activity.PlainActivity
import com.cmt.view.fragment.VideoOpeningDiaogFragment


class MyCoursesDetailsVM : ViewModel() {
    val categoryModel: MutableLiveData<HomeBannerModel> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val subscriptionMessage: MutableLiveData<Boolean> = MutableLiveData()
    val courseName: MutableLiveData<String> = MutableLiveData()
    val pdfData: MutableLiveData<MutableList<HomeBannerModel>> = MutableLiveData()
    val videosThumbnailsData: MutableLiveData<MutableList<VideoModel>> = MutableLiveData()
    val rootVisibility: MutableLiveData<Boolean> = MutableLiveData()

    init {
        rootVisibility.value = false

    }

    fun videoPlay(view: View) {
        val activity = view.context as FragmentActivity
        val dialog = VideoOpeningDiaogFragment()
        dialog.show(activity.supportFragmentManager, null)
    }

    fun setPdfData(view: View, categoryId: String) {
        val activity = view.context as PlainActivity
        activity.activityLoader(true)
        val params = getGlobalParams(activity)
        params[IConstants.Params.category_id] = categoryId ?: ""
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


    }

    fun setThumbnails(view: View, categoryId: String) {
        val activity = view.context as? PlainActivity
        activity?.activityLoader(true)
        val params = HashMap<String, String>()
        params[IConstants.Params.category_id] = categoryId
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity!!)

        BooksVideosApi().sampleVideos(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as APIResponse<*>
                        if (apiResponse.status == IConstants.ServerValidate.valid) {
                            val dataResponse =
                                (apiResponse.data as MutableList<*>).filterIsInstance<VideoModel>()
                                    .toMutableList()
                            videosThumbnailsData.value = dataResponse
                        } else {
                            activity.setSnackBar(response.message)
                        }
                    }
                }
            }
        })


    }


}