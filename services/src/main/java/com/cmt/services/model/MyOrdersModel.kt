package com.cmt.services.model

import java.io.Serializable

data class MyOrdersModel (
    var order_id : String ?= null,
    var id : String ?= null,
    var category_id : String ?= null,
    var purchased_date : String ?= null,
    var name : String ?= null,
    var image : String ?= null,
):Serializable