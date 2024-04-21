package com.cmt.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.R
import com.cmt.databinding.FragmentOtpBinding
import com.cmt.helper.IConstants
import com.cmt.helper.hideKeyboard
import com.cmt.my_doctors.app.AppController
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity
import com.cmt.viewModel.fragment.OtpVM


class OtpFragment(val mobile: String? = null) : Fragment() {
    private lateinit var binding: FragmentOtpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOtpBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@OtpFragment).get(OtpVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@OtpFragment

        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.etOtp1.setInputType(InputType.TYPE_CLASS_NUMBER)
        binding.etOtp2.setInputType(InputType.TYPE_CLASS_NUMBER)
        binding.etOtp3.setInputType(InputType.TYPE_CLASS_NUMBER)
        binding.etOtp4.setInputType(InputType.TYPE_CLASS_NUMBER)

        if (AppController.passwordType == IConstants.Defaults.registration) {
            binding.phoneNumber.text = "+91 $mobile"

        } else if (IConstants.ValueHolder.loginType == "mobile") {
            binding.phoneNumber.text = "+91 $mobile"

        } else if (IConstants.ValueHolder.loginType == "email") {
            binding.phoneNumber.text = "$mobile"

        }
        binding.viewModel?.setDataToView(mobile!!)
        binding.verifyBtn.setOnClickListener {

            if (binding.etOtp1.text.isNullOrEmpty() && binding.etOtp2.text.isNullOrEmpty() &&
                binding.etOtp3.text.isNullOrEmpty() && binding.etOtp4.text.isNullOrEmpty()
            ) {
                binding.viewModel?.errorMsg?.postValue("Please enter OTP")

            } else if (binding.etOtp1.text.isNullOrEmpty() || binding.etOtp2.text.isNullOrEmpty() ||
                binding.etOtp3.text.isNullOrEmpty() || binding.etOtp4.text.isNullOrEmpty()
            ) {
                binding.viewModel?.errorMsg?.postValue("Please enter valid OTP")
            } else {
                binding.viewModel?.errorMsg?.postValue(null)
                view.hideKeyboard()
                binding.viewModel?.verifyOtp(requireActivity())
                /*   if (AppController.passwordType == IConstants.Defaults.forgot_password) {
                       val intent = Intent(requireActivity(), PlainActivity::class.java)
                       intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.chooseNewPassword)
                       requireActivity().startActivity(intent)
                       requireActivity().finishAffinity()
                       requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)

                   }
                   else if (AppController.passwordType == IConstants.Defaults.registration) {
                       val intent = Intent(requireActivity(), MainActivity::class.java)
                       intent.putExtra(
                           IConstants.IntentStrings.type,
                           IConstants.FragmentType.home
                       )
                       requireActivity().startActivity(intent)
                       requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
                       requireActivity().finishAffinity()
                   }*/

            }
        }

    }


}