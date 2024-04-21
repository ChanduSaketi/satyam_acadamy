package com.cmt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.databinding.FragmentForgotPasswordBinding
import com.cmt.viewModel.fragment.ForogotPasswordVM


class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false).apply {
            viewModel =
                ViewModelProvider(this@ForgotPasswordFragment).get(ForogotPasswordVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@ForgotPasswordFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }


}