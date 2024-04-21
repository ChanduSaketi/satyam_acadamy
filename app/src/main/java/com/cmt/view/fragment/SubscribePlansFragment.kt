package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.databinding.FragmentSubscribePlansBinding
import com.cmt.viewModel.fragment.SubscirbeplansVM


class SubscribePlansFragment(val renewalAmount: String? = null) : Fragment() {
    private lateinit var binding: FragmentSubscribePlansBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSubscribePlansBinding.inflate(layoutInflater, container, false).apply {
            viewModel =
                ViewModelProvider(this@SubscribePlansFragment).get(SubscirbeplansVM::class.java)
            lifecycleOwner = this@SubscribePlansFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel?.renewalAmount?.value = renewalAmount ?:""
    }
}