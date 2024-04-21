package com.cmt.view.fragment

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.R
import com.cmt.databinding.FragmentLoginBinding
import com.cmt.databinding.FragmentRegisterBinding
import com.cmt.viewModel.fragment.RegisterVM


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@RegisterFragment).get(RegisterVM::class.java)
            lifecycleOwner = this@RegisterFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mobileNum.setInputType(InputType.TYPE_CLASS_NUMBER)
    }


}