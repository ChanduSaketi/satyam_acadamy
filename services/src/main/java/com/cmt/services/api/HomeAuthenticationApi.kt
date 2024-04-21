package com.cmt.services.api

import com.cmt.services.apiInterface.HomeAPI
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeAuthenticationApi() {

    fun bannersApi(retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(HomeAPI::class.java)
        val call: Call<APIResponse<HomeBannerModel>> = apiClient.bannersApi()

        call.enqueue(object : Callback<APIResponse<HomeBannerModel>> {
            override fun onResponse(
                call: Call<APIResponse<HomeBannerModel>>,
                response: Response<APIResponse<HomeBannerModel>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<HomeBannerModel>>, t: Throwable) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }
        })

    }

    fun advBannersApi(retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(HomeAPI::class.java)
        val call: Call<APIResponse<MutableList<BannerOneModel>>> = apiClient.advBannersApi()

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


    fun deviceIdApi(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(HomeAPI::class.java)
        val call: Call<APIResponse<Any>> = apiClient.deviceId(data)

        call.enqueue(object : Callback<APIResponse<Any>> {
            override fun onResponse(
                call: Call<APIResponse<Any>>,
                response: Response<APIResponse<Any>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<Any>>,
                t: Throwable,
            ) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }
        })
    }

    /*  fun MyCourses(retrofitCallBack: RetrofitCallBack) {
          val apiClient = APIClient().getInstance().create(HomeAPI::class.java)
          val call: Call<APIResponse<MutableList<BannerOneModel>>> = apiClient.advBannersApi()

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
      }*/

    fun allCategories(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(HomeAPI::class.java)
        val call: Call<APIResponse<PageNation<MutableList<HomeBannerModel>>>> =
            apiClient.allCategoriesApi(data)

        call.enqueue(object : Callback<APIResponse<PageNation<MutableList<HomeBannerModel>>>> {
            override fun onResponse(
                call: Call<APIResponse<PageNation<MutableList<HomeBannerModel>>>>,
                response: Response<APIResponse<PageNation<MutableList<HomeBannerModel>>>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<PageNation<MutableList<HomeBannerModel>>>>,
                t: Throwable,
            ) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }
        })

    }

    fun myCourses(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(HomeAPI::class.java)
        val call: Call<APIResponse<PageNation<MutableList<HomeBannerModel>>>> =
            apiClient.myCoursesApi(data)

        call.enqueue(object : Callback<APIResponse<PageNation<MutableList<HomeBannerModel>>>> {
            override fun onResponse(
                call: Call<APIResponse<PageNation<MutableList<HomeBannerModel>>>>,
                response: Response<APIResponse<PageNation<MutableList<HomeBannerModel>>>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<PageNation<MutableList<HomeBannerModel>>>>,
                t: Throwable,
            ) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }
        })

    }

    fun myOrders(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(HomeAPI::class.java)
        val call: Call<APIResponse<PageNation<MutableList<MyOrdersModel>>>> =
            apiClient.myOrdersApi(data)

        call.enqueue(object : Callback<APIResponse<PageNation<MutableList<MyOrdersModel>>>> {
            override fun onResponse(
                call: Call<APIResponse<PageNation<MutableList<MyOrdersModel>>>>,
                response: Response<APIResponse<PageNation<MutableList<MyOrdersModel>>>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<PageNation<MutableList<MyOrdersModel>>>>,
                t: Throwable,
            ) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }
        })

    }


}