package com.cmt.viewModel.fragment

import android.annotation.SuppressLint
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import com.cmt.databinding.FragmentWebViewBinding
import com.cmt.view.activity.PdfActivity


class WebViewVM : ViewModel() {
    lateinit var binding: FragmentWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    fun setUpWebView(url: String?) {
        val activity = binding.root.context as? PdfActivity
        activity?.activityLoader(true)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = MyWebViewClient()
        if (url != null) {
            activity?.activityLoader(false)
            binding.webView.loadUrl(url)
        }
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
            return true
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return true
        }
    }
}