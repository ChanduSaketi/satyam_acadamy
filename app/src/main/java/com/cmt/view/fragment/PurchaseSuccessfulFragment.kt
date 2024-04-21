package com.cmt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.databinding.FragmentPurchaseSuccessfulBinding
import com.cmt.viewModel.fragment.SuccessfulVM

class PurchaseSuccessfulFragment() : Fragment() {
    private lateinit var binding: FragmentPurchaseSuccessfulBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentPurchaseSuccessfulBinding.inflate(layoutInflater, container, false).apply {
                viewModel =
                    ViewModelProvider(this@PurchaseSuccessfulFragment).get(SuccessfulVM::class.java)
                lifecycleOwner = this@PurchaseSuccessfulFragment

            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel?.setData()
    }


}