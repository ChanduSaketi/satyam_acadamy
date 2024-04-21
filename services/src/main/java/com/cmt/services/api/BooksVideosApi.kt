package com.cmt.services.api

import com.cmt.services.apiInterface.BooksVideosAPI
import com.cmt.services.apiInterface.HomeAPI
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksVideosApi {

    fun sampleBooks(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(BooksVideosAPI::class.java)
        val call: Call<APIResponse<MutableList<HomeBannerModel>>> = apiClient.sampleBooksApi(data)

        call.enqueue(object : Callback<APIResponse<MutableList<HomeBannerModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<HomeBannerModel>>>,
                response: Response<APIResponse<MutableList<HomeBannerModel>>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<HomeBannerModel>>>,
                t: Throwable,
            ) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }
        })

    }

    fun purchasedCheck(retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(BooksVideosAPI::class.java)
        val call: Call<APIResponse<Any>> = apiClient.coursePurchasedCheck()

        call.enqueue(object : Callback<APIResponse<Any>> {
            override fun onResponse(
                call: Call<APIResponse<Any>>,
                response: Response<APIResponse<Any>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<Any>>, t: Throwable) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }
        })

    }

    fun upComingBooks(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(BooksVideosAPI::class.java)
        val call: Call<APIResponse<MutableList<HomeBannerModel>>> = apiClient.upcomingBooksApi(data)

        call.enqueue(object : Callback<APIResponse<MutableList<HomeBannerModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<HomeBannerModel>>>,
                response: Response<APIResponse<MutableList<HomeBannerModel>>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<HomeBannerModel>>>,
                t: Throwable,
            ) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }
        })

    }

    fun CourseSliders(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(BooksVideosAPI::class.java)
        val call: Call<APIResponse<MutableList<BannerOneModel>>> = apiClient.courseSliders(data)

        call.enqueue(object : Callback<APIResponse<MutableList<BannerOneModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<BannerOneModel>>>,
                response: Response<APIResponse<MutableList<BannerOneModel>>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<BannerOneModel>>>,
                t: Throwable,
            ) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }
        })

    }

    fun sampleVideos(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(BooksVideosAPI::class.java)
        val call: Call<APIResponse<MutableList<VideoModel>>> = apiClient.sampVideosApi(data)

        call.enqueue(object : Callback<APIResponse<MutableList<VideoModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<VideoModel>>>,
                response: Response<APIResponse<MutableList<VideoModel>>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<MutableList<VideoModel>>>, t: Throwable) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }
        })

    }

    fun checkOut(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(BooksVideosAPI::class.java)
        val call: Call<APIResponse<CheckOutModel>> = apiClient.checkOutApi(data)

        call.enqueue(object : Callback<APIResponse<CheckOutModel>> {
            override fun onResponse(
                call: Call<APIResponse<CheckOutModel>>,
                response: Response<APIResponse<CheckOutModel>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<CheckOutModel>>, t: Throwable) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }
        })

    }
}