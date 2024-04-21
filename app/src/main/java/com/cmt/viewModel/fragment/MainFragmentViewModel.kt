package com.cmt.viewModel.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.R
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.HomeAuthenticationApi
import com.cmt.services.api.Profile
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.*
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity

class MainFragmentViewModel : ViewModel() {
    var bannersData: MutableLiveData<MutableList<BannerOneModel>> = MutableLiveData()
    var bottomBannersData: MutableLiveData<MutableList<BannerOneModel>> = MutableLiveData()
    var stickyBanners: MutableLiveData<MutableList<BannerOneModel>> = MutableLiveData()
    var sampCategoriesData: MutableLiveData<PageNation<*>> = MutableLiveData()


    fun setBanners(view: View) {
        val activity = view.context as MainActivity
        homeBanners(activity)
    }


    /*   fun categoryDetails(view : View){
           val activity = view.context as FragmentActivity
           val intent = Intent(activity, PlainActivity::class.java)
           intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.subCategories)
           activity.startActivity(intent)
           activity.overridePendingTransition(R.anim.enter, R.anim.exit)
       }*/


    fun categoriesViewAll(view: View) {
        val activity = view.context as FragmentActivity
        val intent = Intent(activity, PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.categeories)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)

    }


    fun homeBanners(context: Context) {
        val activity = context as MainActivity
        activity.activityLoader(true)
        HomeAuthenticationApi().bannersApi(object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as APIResponse<*>

                        if (apiResponse.status == IConstants.ServerValidate.valid) {
                            val dataResponse = apiResponse.data as? HomeBannerModel
                            bannersData.value = dataResponse?.top_banners!!
                            bottomBannersData.value = dataResponse.bottom_banners!!
                        }
                    }
                }
            }
        })
    }


    fun setAdvBanners(context: Context) {
        val activity = context as MainActivity
        activity.activityLoader(true)
        HomeAuthenticationApi().advBannersApi(object : RetrofitCallBack {
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
                                (apiResponse.data as? MutableList<*>)?.filterIsInstance<BannerOneModel>()
                                    ?.toMutableList()
                            stickyBanners.value = dataResponse

                        }
                    }
                }
            }

        })
    }



        fun setCategories(view: View, pageCount: String) {
            val activity = view.context as MainActivity
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
                                sampCategoriesData.value = dataResponse
                            } else {
                                activity.setSnackBar(error)
                            }
                        }
                    }
                }

            })

        }

}