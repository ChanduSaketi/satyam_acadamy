package com.cmt.services.model

import java.io.Serializable

data class CheckOutModel(
    val name: String? = null,
    val category_id: String? = null,
    val image: String? = null,
    val price: String? = null,
    val subscription_price: String? = null,
    val total_price: String? = null,
) : Serializable