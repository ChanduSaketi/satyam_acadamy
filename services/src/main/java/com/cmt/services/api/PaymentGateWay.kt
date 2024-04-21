package com.cmt.services.api

import com.cmt.services.apiInterface.PaymentAPI
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentGateWay {

    fun paymentMethod(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(PaymentAPI::class.java)
        val call: Call<ResponseBody> = apiClient.paymentGatewayApi(data)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    val stringResponse = response.body()?.let { String(it.bytes()) }
                    val jsonObject = JSONObject(stringResponse)
                    retrofitCallBack.responseListener(jsonObject)

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }

        })
    }

    fun paymentOrderSuccessMethod(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(PaymentAPI::class.java)
        val call: Call<APIResponse<Any>> = apiClient.orderSuccessApi(data)

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

    fun paymentOrderFailureMethod(data: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(PaymentAPI::class.java)
        val call: Call<APIResponse<Any>> = apiClient.orderFailureApi(data)

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