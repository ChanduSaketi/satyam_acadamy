package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.*
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface HomeAPI {

    @GET(IServiceConstants.API.banners)
    fun bannersApi(): Call<APIResponse<HomeBannerModel>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.store_device_id)
    fun deviceId(@FieldMap data: HashMap<String, String>): Call<APIResponse<Any>>

    @GET(IServiceConstants.API.ad_banners)
    fun advBannersApi(): Call<APIResponse<MutableList<BannerOneModel>>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.categories)
    fun allCategoriesApi(@FieldMap data: HashMap<String, String>): Call<APIResponse<PageNation<MutableList<HomeBannerModel>>>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.my_courses)
    fun myCoursesApi(@FieldMap data: HashMap<String, String>): Call<APIResponse<PageNation<MutableList<HomeBannerModel>>>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.my_orders)
    fun myOrdersApi(@FieldMap data: HashMap<String, String>): Call<APIResponse<PageNation<MutableList<MyOrdersModel>>>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.my_membership)
    fun myMemberShipApi(@FieldMap data: HashMap<String, String>): Call<APIResponse<PageNation<MutableList<HomeBannerModel>>>>


}
