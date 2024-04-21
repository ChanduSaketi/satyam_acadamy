package com.cmt.viewModel.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlertDialogVM : ViewModel() {
    val message: MutableLiveData<String> = MutableLiveData()

    fun setData(alert: String) {
        message.value = alert
    }
}