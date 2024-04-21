package com.cmt.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.cmt.R
import com.cmt.databinding.FragmentMembershipBinding
import com.cmt.helper.IConstants
import com.cmt.view.activity.PlainActivity
import com.cmt.viewModel.fragment.MembershipVM

class MembershipFragment : Fragment() {
    private lateinit var binding: FragmentMembershipBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMembershipBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@MembershipFragment).get(MembershipVM::class.java)
            lifecycleOwner = this@MembershipFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel?.membership(view)

        binding.btnRenewal.setOnClickListener {
            binding.viewModel?.model?.observe(requireActivity()){
                if (it?.is_subscribed == 0) {
                    val intent = Intent(activity, PlainActivity::class.java)
                    intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.subscirbePlan)
                    intent.putExtra(IConstants.IntentStrings.payload, it.renewal_amount ?: "")
                    requireActivity().startActivity(intent)
                    requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
                } else {
                    Toast.makeText(activity, "You are already a subscribed user", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


}