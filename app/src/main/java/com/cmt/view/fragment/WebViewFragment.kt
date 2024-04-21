package com.cmt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.databinding.FragmentWebViewBinding
import com.cmt.viewModel.fragment.WebViewVM


class WebViewFragment(private val url: String?) : Fragment() {
    lateinit var binding: FragmentWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@WebViewFragment).get(WebViewVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@WebViewFragment

        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel?.setUpWebView(url)
    }

}