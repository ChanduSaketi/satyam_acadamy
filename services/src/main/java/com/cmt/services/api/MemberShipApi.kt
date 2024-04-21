package com.cmt.services.api

import com.cmt.services.apiInterface.MyMemberShip
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.MembershipModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberShipApi {

    fun MymemberShipApi(data : HashMap<String , String >,retrofitCallBack: RetrofitCallBack) {
        val apiClient = APIClient().getInstance().create(MyMemberShip::class.java)
        val call: Call<APIResponse<MembershipModel>> = apiClient.memberShipAPI(data)

        call.enqueue(object : Callback<APIResponse<MembershipModel>> {
            override fun onResponse(
                call: Call<APIResponse<MembershipModel>>,
                response: Response<APIResponse<MembershipModel>>,
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MembershipModel>>,
                t: Throwable,
            ) {
                t.printStackTrace()
                retrofitCallBack.responseListener(null, t.message ?: "Please contact to Admin")
            }
        })
    }
}