package com.cmt.viewModel.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.services.model.MyOrdersModel

class OrderDetailsVM : ViewModel() {
    val image: MutableLiveData<String> = MutableLiveData()
    val courseName: MutableLiveData<String> = MutableLiveData()
    val dateTime: MutableLiveData<String> = MutableLiveData()

    fun setData(model: MyOrdersModel) {
        image.value = model.image
        courseName.value = model.name
        dateTime.value = model.purchased_date
    }
}