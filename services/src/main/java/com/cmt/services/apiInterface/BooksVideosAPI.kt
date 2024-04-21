package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.*
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BooksVideosAPI {

    @FormUrlEncoded
    @POST(IServiceConstants.API.category_books)
    fun sampleBooksApi(@FieldMap data: HashMap<String, String>): Call<APIResponse<MutableList<HomeBannerModel>>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.category_videos)
    fun sampVideosApi(@FieldMap data: HashMap<String, String>): Call<APIResponse<MutableList<VideoModel>>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.full_videos)
    fun fullvideosApi(@FieldMap data: HashMap<String, String>): Call<APIResponse<MutableList<VideoModel>>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.upcoming_books)
    fun upcomingBooksApi(@FieldMap data: HashMap<String, String>): Call<APIResponse<MutableList<HomeBannerModel>>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.course_sliders)
    fun courseSliders(@FieldMap data: HashMap<String, String>): Call<APIResponse<MutableList<BannerOneModel>>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.checkout)
    fun checkOutApi(@FieldMap data: HashMap<String, String>): Call<APIResponse<CheckOutModel>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.category_purchase_check)
    fun coursePurchasedCheck(): Call<APIResponse<Any>>


}