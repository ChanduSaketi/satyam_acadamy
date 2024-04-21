package com.cmt.services.api

import com.cmt.services.apiInterface.Authentication
import com.cmt.services.apiInterface.ProfileAPI
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class Profile {

    fun showProfile(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(ProfileAPI::class.java)
        val call: Call<APIResponse<UserDetailsModel>> = apiClient.showProfile(data)

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
    fun editProfile(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(ProfileAPI::class.java)
        val call: Call<APIResponse<Any>> = apiClient.updateProfile(data)

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


    fun updateProfile(data: HashMap<String, RequestBody>, file: File?, retrofitCallBack: RetrofitCallBack) {
        val apiClient: ProfileAPI = APIClient().getInstance().create(ProfileAPI::class.java)
        var partBody: MultipartBody.Part?=null
        file?.let {
            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            partBody = MultipartBody.Part.createFormData("image", file.name, requestBody)
        }

        val call: Call<APIResponse<Any>> = apiClient.update(partBody, data)
        call.enqueue(object : Callback<APIResponse<Any>> {
            override fun onResponse(
                call: Call<APIResponse<Any>>,
                response: Response<APIResponse<Any>>
            ) {
                retrofitCallBack.responseListener(response = response.body())
            }

            override fun onFailure(call: Call<APIResponse<Any>>, t: Throwable) {
                val error = t.message ?: "not found"
                retrofitCallBack.responseListener(response = null, error = error)
                t.printStackTrace()
            }

        })

    }

}