package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ProfileAPI {

    @FormUrlEncoded
    @POST(IServiceConstants.API.profile_show)
    fun showProfile(@FieldMap data: HashMap<String, String>): Call<APIResponse<UserDetailsModel>>


    @FormUrlEncoded
    @POST(IServiceConstants.API.profile_update)
    fun updateProfile(@FieldMap data: HashMap<String, String>): Call<APIResponse<Any>>

    @Multipart
    @POST(IServiceConstants.API.profile_update)
    fun update(
        @Part profileImage: MultipartBody.Part? = null,
        @PartMap data: HashMap<String, RequestBody>
    ): Call<APIResponse<Any>>


}