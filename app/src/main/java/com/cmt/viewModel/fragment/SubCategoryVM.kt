package com.cmt.viewModel.fragment

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.R
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.BooksVideosApi
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.BannerOneModel
import com.cmt.services.model.CheckOutModel
import com.cmt.services.model.HomeBannerModel
import com.cmt.view.activity.PlainActivity
import com.cmt.view.fragment.AlertDialogFragment

class SubCategoryVM : ViewModel() {
    val sliderData: MutableLiveData<MutableList<BannerOneModel>> = MutableLiveData()
    val bannerModel: MutableLiveData<HomeBannerModel> = MutableLiveData()
    val checkOutModel: MutableLiveData<CheckOutModel> = MutableLiveData()
    val rootVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val isCouseSubscribed: MutableLiveData<Boolean> = MutableLiveData()
    val isDataAvailable: MutableLiveData<Boolean> = MutableLiveData()
    val isPurchased: MutableLiveData<Int> = MutableLiveData()


    init {
        bannerModel.value = HomeBannerModel()
        rootVisibility.value = false
        isCouseSubscribed.value = false
        isDataAvailable.value = true
        isPurchased.value = 0

    }

    fun setSliders(view: View, model: HomeBannerModel) {
        bannerModel.value = model
        val activity = view.context as PlainActivity
        activity.activityLoader(true)
        val params = HashMap<String, String>()
        params[IConstants.Params.category_id] = model.id ?: ""
        BooksVideosApi().CourseSliders(params, object : RetrofitCallBack {
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
                                (apiResponse.data as MutableList<*>).filterIsInstance<BannerOneModel>()
                                    .toMutableList()
                            sliderData.value = dataResponse
                        } else {
                            activity.setSnackBar(apiResponse.message)
                        }
                    }
                }
            }

        })
    }


        fun checkPurchasedCourse(view: View) {
            val activity = view.context as PlainActivity
            activity.activityLoader(true)

            BooksVideosApi().purchasedCheck(object : RetrofitCallBack {
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
                                    apiResponse.data as? MutableList<*>
                                isPurchased.value = 1

                            } else {
                                isPurchased.value = 0
//                            activity.setSnackBar(apiResponse.message)

                            }

                        }
                    }
                }

            })
        }


/*        val data = mutableListOf<BannerOneModel>()
        for (i in 1..4) {
//            data.add(BannerOneModel(R.drawable.banner_2))
        }
        sliderData.postValue(data)*/

        fun checkOut(view: View, model: HomeBannerModel) {
            bannerModel.value = model
            val activity = view.context as PlainActivity
            activity.activityLoader(true)
            val params = HashMap<String, String>()
            params[IConstants.Params.user_id] = AppPreferences().getUserId(activity)
            params[IConstants.Params.category_id] = bannerModel.value?.id ?: ""
            BooksVideosApi().checkOut(params, object : RetrofitCallBack {
                override fun responseListener(response: Any?, error: String?) {
                    activity.activityLoader(false)
                    when {
                        error != null -> {
                            activity.setSnackBar(error)
                        }
                        response != null -> {
                            val apiResponse = response as APIResponse<*>
                            if (apiResponse.status == IConstants.ServerValidate.valid) {
                                val dataResponse = apiResponse.data as CheckOutModel
                                checkOutModel.value = dataResponse
                                isCouseSubscribed.postValue(false)


                            } else {
                                isCouseSubscribed.postValue(true)

                            }
                        }
                    }

                }

            })

        }


        fun clickCheckOut(view: View) {
            val activity = view.context as PlainActivity
            val intent = Intent(activity, PlainActivity::class.java)
            intent.putExtra(IConstants.IntentStrings.type,
                IConstants.FragmentType.cart)
            intent.putExtra(IConstants.IntentStrings.payload, checkOutModel.value)
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.enter, R.anim.exit)
        }

}