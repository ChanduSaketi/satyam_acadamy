package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PaymentAPI {

    @FormUrlEncoded
    @POST(IServiceConstants.API.order)
    fun paymentGatewayApi(@FieldMap data : HashMap<String,String>): Call<ResponseBody>

    @FormUrlEncoded
    @POST(IServiceConstants.API.order_success)
    fun orderSuccessApi(@FieldMap data: HashMap<String, String>): Call<APIResponse<Any>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.order_failure)
    fun orderFailureApi(@FieldMap data: HashMap<String, String>): Call<APIResponse<Any>>
}