package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.R
import com.cmt.databinding.FragmentChangePasswordBinding
import com.cmt.viewModel.fragment.ChangePasswordVM


class ChangePasswordFragment : Fragment() {

    private lateinit var binding: FragmentChangePasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentChangePasswordBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@ChangePasswordFragment).get(ChangePasswordVM::class.java)
            lifecycleOwner = this@ChangePasswordFragment

        }
        return binding.root
    }


}