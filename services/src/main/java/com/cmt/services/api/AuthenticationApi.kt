package com.cmt.services.api

import com.cmt.services.apiInterface.Authentication
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.HashMap

class AuthenticationApi {
    fun registerApi(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<UserDetailsModel>> = apiClient.register(data)

        call.enqueue(object : Callback<APIResponse<UserDetailsModel>> {
            override fun onResponse(
                call: Call<APIResponse<UserDetailsModel>>,
                response: Response<APIResponse<UserDetailsModel>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<UserDetailsModel>>, t: Throwable) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }

        })
    }

    fun loginApi(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<UserDetailsModel>> = apiClient.login(data)

        call.enqueue(object : Callback<APIResponse<UserDetailsModel>> {
            override fun onResponse(
                call: Call<APIResponse<UserDetailsModel>>,
                response: Response<APIResponse<UserDetailsModel>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<UserDetailsModel>>, t: Throwable) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }

        })
    }

    fun autoLoginApi(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<UserDetailsModel>> = apiClient.autoLogin(data)

        call.enqueue(object : Callback<APIResponse<UserDetailsModel>> {
            override fun onResponse(
                call: Call<APIResponse<UserDetailsModel>>,
                response: Response<APIResponse<UserDetailsModel>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<UserDetailsModel>>, t: Throwable) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }

        })
    }
    fun logoutApi(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<Any>> = apiClient.logout(data)

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

    fun otpApi(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<UserDetailsModel>> = apiClient.otp(data)

        call.enqueue(object : Callback<APIResponse<UserDetailsModel>> {
            override fun onResponse(
                call: Call<APIResponse<UserDetailsModel>>,
                response: Response<APIResponse<UserDetailsModel>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<UserDetailsModel>>, t: Throwable) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }
        })
    }

    fun resendOtpApi(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<UserDetailsModel>> = apiClient.resend_otp(data)

        call.enqueue(object : Callback<APIResponse<UserDetailsModel>> {
            override fun onResponse(
                call: Call<APIResponse<UserDetailsModel>>,
                response: Response<APIResponse<UserDetailsModel>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<UserDetailsModel>>, t: Throwable) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }

        })
    }

    fun forgotPassword(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<UserDetailsModel>> = apiClient.forgotPassword(data)

        call.enqueue(object : Callback<APIResponse<UserDetailsModel>> {
            override fun onResponse(
                call: Call<APIResponse<UserDetailsModel>>,
                response: Response<APIResponse<UserDetailsModel>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<UserDetailsModel>>, t: Throwable) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }

        })
    }

    fun changePasswordApi(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<Any>> = apiClient.changePassword(data)

        call.enqueue(object : Callback<APIResponse<Any>> {
            override fun onResponse(
                call: Call<APIResponse<Any>>,
                response: Response<APIResponse<Any>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<Any>>, t: Throwable) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please Contact to Admin")
            }

        })
    }
}

