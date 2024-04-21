package com.cmt.helper

import com.cmt.services.model.UserDetailsModel

interface IConstants {

    object IntentStrings {
        const val type = "type"
        const val dialog = "dialog"
        const val payload = "payload"
        const val category_id = "category_id"
        const val description = "category_id"
        const val userId = "userId"
        const val price = "price"
    }


    object FragmentType {
        const val login = "login"
        const val register = "register"
        const val otp = "otp"
        const val home = "home"
        const val subCategories = "subCategories"
        const val myOrders = "myOrders"
        const val cart = "cart"
        const val myCoursesDetails = "myCoursesDetails"
        const val myCourses = "myCourses"
        const val purchased_successful = "purchased_successful"
        const val editProfile = "editProfile"
        const val subscirbePlan = "subscirbePlan"
        const val order_details = "order_details"
        const val material = "material"
        const val videosPlay = "videosPlay"
        const val membership = "membership"
        const val AboutUS = "AboutUS"
        const val PrivacyPolicy = "PrivacyPolicy"
        const val TermsConditions = "TermsConditions"
        const val myProfile = "myProfile"
        const val chooseNewPassword = "chooseNewPassword"
        const val changePassword = "changePassword"
        const val Notifications = "Notifications"

        const val forgotPassword = "forgotPassword"
        const val categeories = "categeories"
        const val payment = "payment"
    }


    object ServerValidate {
        const val valid: String = "valid"
        const val invalid: String = "invalid"
        const val status: String = "status"
        const val data: String = "data"
        const val message: String = "message"
    }

    object ValueHolder {
        var loginType: String? = null
        var userData: UserDetailsModel? = null
    }

    object
    Params {
        const val version = "version_code"
        const val device_type = "device_type"
        const val access_token = "access_token"
        const val push_notification_key = "push_notification_key"
        const val name = "name"
        const val mobile = "mobile"
        const val email = "email"
        const val password = "password"
        const val login_id = "login_id"
        const val verification_code = "verification_code"
        const val token = "token"
        const val image = "image"
        const val page_no = "page_no"
        const val user_id = "user_id"
        const val device_id = "device_id"
        const val category_id = "category_id"
        const val price = "price"
        const val old_password = "old_password"
        const val new_password = "new_password"
        const val confirm_password = "confirm_password"
        const val payment_id = "payment_id"
        const val razorpay_signature = "razorpay_signature"
        const val razorpay_payment_id = "razorpay_payment_id"
        const val razorpay_order_id = "razorpay_order_id"


    }

    object Defaults {
        const val request_from = "android"
        const val forgot_password = "forgot_password"
        const val registration = "registration"
        const val login = "login"
        const val mobile = "mobile"
        const val email = "email"
        const val clear_cart = "clear_cart"
        const val logout = "logout"
        const val cart = "cart"
        const val subscription = "subscription"
        const val my_courses = "my_courses"
        const val home = "home"

    }

    enum class ObserverAppNavEvents {
        OPEN_GUID_ACTIVITY, OPEN_MAIN_ACTIVITY, OPEN_FORGOT_PWD_SCREEN, OPEN_LOGIN_SCREEN, OPEN_OTP_SCREEN,
        OPEN_WELCOME_SCREEN, PROFILE_READY_SCREEN, OPEN_NOTIFICATION, MY_APPOINTMENTS, BOOKING_SUMMARY, CHECK_OUT,
    }


    enum class ObserverEvents {
        START_LOADER, STOP_LOADER, ON_BACK_PRESSED, FORGOT_PWD_SUCCESS, OPEN_MENU, OPEN_CALENDAR, MAKE_PAYMENT, SHARE_APP, OPEN_BOTTOM_SHEET,
        DIALOG_DISMISS, BACK, OPEN_IMAGE_PICKER
    }

    object Pref {
        const val shared_preference = "shared_preference"
        const val token = Params.token
        const val user_id = "user_id"
        const val push_notification_token = Params.push_notification_key
    }

    object PermissionCode {
        const val app_permission = 1000
        const val storage_camera = 1001
        const val location = 1002
    }

    object Response {
        const val valid = "valid"
    }
}