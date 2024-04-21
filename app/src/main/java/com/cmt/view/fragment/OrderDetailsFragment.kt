package com.cmt.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.databinding.FragmentOrderDetailsBinding
import com.cmt.services.model.MyOrdersModel
import com.cmt.viewModel.fragment.OrderDetailsVM

class OrderDetailsFragment(val model: MyOrdersModel? = null) : Fragment() {

    private lateinit var binding: FragmentOrderDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOrderDetailsBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@OrderDetailsFragment).get(OrderDetailsVM::class.java)
            lifecycleOwner = this@OrderDetailsFragment

        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.purchasedDate.text = model?.purchased_date
        binding.viewModel?.setData(model!!)
    }

}