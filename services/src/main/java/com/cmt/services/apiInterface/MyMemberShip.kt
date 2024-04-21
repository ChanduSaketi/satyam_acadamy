package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.MembershipModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyMemberShip {

    @FormUrlEncoded
    @POST(IServiceConstants.API.my_membership)
    fun memberShipAPI(@FieldMap data: HashMap<String, String>): Call<APIResponse<MembershipModel>>
}