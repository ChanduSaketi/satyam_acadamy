package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.R
import com.cmt.databinding.FragmentChooseNewPasswordBinding
import com.cmt.viewModel.fragment.ChooseNewPasswordVM


class ChooseNewPasswordFragment : Fragment() {
    private lateinit var binding: FragmentChooseNewPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentChooseNewPasswordBinding.inflate(layoutInflater, container, false).apply {
            viewModel =
                ViewModelProvider(this@ChooseNewPasswordFragment).get(ChooseNewPasswordVM::class.java)
            lifecycleOwner = this@ChooseNewPasswordFragment

        }
        return binding.root
    }


}