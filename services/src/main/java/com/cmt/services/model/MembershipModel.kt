package com.cmt.services.model

data class MembershipModel(
    val id: String? = null,
    val user_id: String? = null,
    val subscription_date: String? = null,
    val subscription_expiry_date: String? = null,
    val renewal_amount: String? = null,
    val first_time_user: Int? = null,
    val user_check: String? = null,
    val is_subscribed: Int? = null,
)