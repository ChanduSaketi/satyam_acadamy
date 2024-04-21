package com.cmt.view.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.cmt.R
import com.cmt.databinding.FragmentAlertDialogBinding
import com.cmt.viewModel.fragment.AlertDialogVM


class AlertDialogFragment(val message: String? = null) : DialogFragment() {

    private lateinit var binding: FragmentAlertDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = FragmentAlertDialogBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@AlertDialogFragment).get(AlertDialogVM::class.java)
            lifecycleOwner = this@AlertDialogFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel?.setData(message!!)

        binding.okBtn.setOnClickListener {
            dialog?.dismiss()
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val param = binding.root.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(25,10,25,20)
        binding.root.layoutParams = param
    }
}