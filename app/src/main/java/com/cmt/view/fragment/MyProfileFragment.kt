package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.R
import com.cmt.databinding.FragmentMyProfileBinding
import com.cmt.helper.IConstants
import com.cmt.my_doctors.app.AppController
import com.cmt.viewModel.fragment.ProfileVM


class MyProfileFragment : Fragment() {

    private lateinit var binding: FragmentMyProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMyProfileBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@MyProfileFragment).get(ProfileVM::class.java)
            lifecycleOwner = this@MyProfileFragment

        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.viewModel?.setData(requireActivity())

    }


}