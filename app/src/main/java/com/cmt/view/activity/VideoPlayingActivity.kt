package com.cmt.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.cmt.databinding.ActivityVideoPlayingBinding
import com.cmt.helper.IConstants

class VideoPlayingActivity() : AppCompatActivity() {
    lateinit var binding: ActivityVideoPlayingBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoId = intent.getStringExtra(IConstants.IntentStrings.payload) as String
        val videoUrl = "https://player.vimeo.com/video/" + videoId

        binding.webView.getSettings().pluginState = WebSettings.PluginState.ON;
        binding.webView.getSettings().pluginState = WebSettings.PluginState.ON_DEMAND;
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)
        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.settings.mediaPlaybackRequiresUserGesture = false
        binding.webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        binding.progressBar.visibility = View.GONE
        val data_html =
            "<!DOCTYPE html><html> <head> <meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"target-densitydpi=high-dpi\" /> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <link rel=\"stylesheet\"  href=\"hdpi.css\" /></head> <body style=\"background:black;margin:0 0 0 0; padding:0 0 0 0;\"><iframe src=\"$videoUrl?autoplay=1\" \n\" +\n" +
                    "        \"        width=\"100%\" \n\" +\n" +
                    "        \"        height=\"100%\" \n\" +\n" +
                    "        \"        frameborder=\"0\" \n\" +\n" +
                    "        \"        webkitallowfullscreen mozallowfullscreen allowfullscreen style=\"position:absolute;top:0;left:0;width:100%;height:100%;\" ></iframe> </body> </html> "





        binding.webView.loadDataWithBaseURL(videoUrl,
            data_html,
            "text/html",
            "UTF-8",
            null)
    }

}