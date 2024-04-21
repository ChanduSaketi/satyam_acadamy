package com.cmt.viewModel.fragment

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.BooksVideosApi
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.HomeBannerModel
import com.cmt.services.model.VideoModel
import com.cmt.view.activity.PlainActivity

class VideosVM : ViewModel() {
    val videosThumbnailsData: MutableLiveData<MutableList<VideoModel>> = MutableLiveData()
    val rootVisibility: MutableLiveData<Boolean> = MutableLiveData()

    init {
        rootVisibility.value = false

    }

    fun setThumbnails(view: View, model: HomeBannerModel) {
        val activity = view.context as? PlainActivity
        activity?.activityLoader(true)
        val params = HashMap<String, String>()
        params[IConstants.Params.category_id] = model.id ?: ""
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity!!)

        BooksVideosApi().sampleVideos(params, object : RetrofitCallBack {
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