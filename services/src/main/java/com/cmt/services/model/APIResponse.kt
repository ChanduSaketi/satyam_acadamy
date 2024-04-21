package com.cmt.services.model

class APIResponse<T> {
    var status: String? = null
    var message: String? = null
    var data: T? = null
}

class PageNation<T> {
    var total_results_found: Int? = null
    var total_pages: Int? = null
    var results: T? = null
}
