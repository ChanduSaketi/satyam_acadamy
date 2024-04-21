package com.cmt.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.R
import com.cmt.databinding.FragmentCartBinding
import com.cmt.helper.IConstants
import com.cmt.helper.makeLinks
import com.cmt.services.model.CheckOutModel
import com.cmt.services.model.HomeBannerModel
import com.cmt.view.activity.MainActivity
import com.cmt.viewModel.fragment.CartVM


class CartFragment(val model: CheckOutModel? = null) : Fragment() {
    private lateinit var binding: FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@CartFragment).get(CartVM::class.java)
            lifecycleOwner = this@CartFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel?.checkOut(view, model!!)


        binding.noItems.makeLinks(
            Pair("Home", View.OnClickListener {
                startActivity(
                    Intent(requireActivity(), MainActivity
                    ::class.java)
                        .putExtra(
                            IConstants.IntentStrings.type,
                            IConstants.FragmentType.home)
                )
                requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
                requireActivity().finishAffinity()
            }),
        )

    }


}