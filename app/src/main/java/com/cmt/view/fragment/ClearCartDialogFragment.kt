package com.cmt.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.cmt.R
import com.cmt.databinding.FragmentClearCartDialogBinding
import com.cmt.helper.AppPreferences
import com.cmt.helper.ICallback
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.my_doctors.app.AppController
import com.cmt.services.api.AuthenticationApi
import com.cmt.services.apiInterface.Authentication
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.view.activity.PlainActivity


class ClearCartDialogFragment(val mCallBack: ICallback? = null) : DialogFragment() {
    private lateinit var binding: FragmentClearCartDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dialog?.window?.setFlags(WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE);
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = FragmentClearCartDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (AppController.dialogType == IConstants.Defaults.clear_cart) {
            binding.confirmationMessage.text =
                "This will clear the item in your cart. Do you want to proceed?"
        } else if (AppController.dialogType == IConstants.Defaults.logout) {
            binding.confirmationMessage.text = "Do you really want to logout?"

        }
        binding.cancelBtn.setOnClickListener {
            dialog?.dismiss()
        }
        binding.clearBtn.setOnClickListener {
            if (AppController.dialogType == IConstants.Defaults.clear_cart) {
                mCallBack?.delegate(
                    any = Any()
                )
                dialog?.dismiss()
            } else if (AppController.dialogType == IConstants.Defaults.logout) {
                logout()
            }


        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val param = binding.root.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(25, 10, 25, 20)
        binding.root.layoutParams = param
    }

    fun logout() {
       /* val intent = Intent(requireActivity(), PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type,
            IConstants.FragmentType.login)
        requireActivity().startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
        requireActivity().finishAffinity()*/
        val activity = requireActivity()
        val params = HashMap<String, String>()
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity)
        AuthenticationApi().logoutApi(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                when {
                    error != null -> {
                        activity.setSnackBar(error)
                    }
                    response != null -> {
                        val apiResponse = response as APIResponse<*>
                        if (apiResponse.status == IConstants.ServerValidate.valid) {
                            AppPreferences().logout(activity)

                            Toast.makeText(activity, "${apiResponse.message}", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(requireActivity(), PlainActivity::class.java)
                            intent.putExtra(IConstants.IntentStrings.type,
                                IConstants.FragmentType.login)
                            requireActivity().startActivity(intent)
                            requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
                            requireActivity().finishAffinity()

                        } else {
                            activity.setSnackBar(apiResponse.message)
                        }
                    }
                }
            }

        })
    }

}


