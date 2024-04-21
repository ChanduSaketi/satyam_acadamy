package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Authentication {

    @FormUrlEncoded
    @POST(IServiceConstants.API.register)
    fun register(@FieldMap data: HashMap<String, String>): Call<APIResponse<UserDetailsModel>>



    @FormUrlEncoded
    @POST(IServiceConstants.API.login)
    fun login(@FieldMap data: HashMap<String, String>): Call<APIResponse<UserDetailsModel>>


    @FormUrlEncoded
    @POST(IServiceConstants.API.auto_login)
    fun autoLogin(@FieldMap data: HashMap<String, String>): Call<APIResponse<UserDetailsModel>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.logout)
    fun logout(@FieldMap data: HashMap<String, String>): Call<APIResponse<Any>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.verify_otp)
    fun otp(@FieldMap data: HashMap<String, String>): Call<APIResponse<UserDetailsModel>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.resend_otp)
    fun resend_otp(@FieldMap data: HashMap<String, String>): Call<APIResponse<UserDetailsModel>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.forgotpassword)
    fun forgotPassword(@FieldMap data: HashMap<String, String>): Call<APIResponse<UserDetailsModel>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.change_password)
    fun changePassword(@FieldMap data: HashMap<String, String>): Call<APIResponse<Any>>

}