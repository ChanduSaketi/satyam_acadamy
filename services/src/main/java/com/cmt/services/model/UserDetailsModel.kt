package com.cmt.services.model

import java.io.Serializable

data class UserDetailsModel (
    var id: String? = null,
    var password: String? = null,
    var name: String? = null,
    var mobile: String? = null,
    var is_verified: Int? = null,
    var email: String? = null,
    var new_password: String? = null,
    var confirm_password: String? = null,
    var image: String? = null,
    var profile_image: String? = null,
    var token: String? = null

): Serializable