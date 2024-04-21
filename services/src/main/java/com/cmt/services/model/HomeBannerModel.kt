package com.cmt.services.model

import java.io.Serializable

data class HomeBannerModel(
    var image1: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var id: String? = null,
    var image: String? = null,
    var price: String? = null,
    var category_id: String? = null,
    var cover_image: String? = null,
    var book_url: String? = null,
    var is_subscribed: Int? = null,
    var is_purchased : Int ?= null,
    val top_banners: MutableList<BannerOneModel>? = null,
    val bottom_banners: MutableList<BannerOneModel>? = null,
) : Serializable

class BannerOneModel {
    var id: String? = null
    var image: String? = null
    var display_order: String? = null
}
