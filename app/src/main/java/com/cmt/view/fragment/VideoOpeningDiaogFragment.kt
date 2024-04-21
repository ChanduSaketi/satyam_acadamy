package com.cmt.view.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.cmt.databinding.FragmentVideoOpeningDiaogBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class VideoOpeningDiaogFragment : DialogFragment() {
    private lateinit var binding: FragmentVideoOpeningDiaogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        dialog?.setCanceledOnTouchOutside(false)
        binding = FragmentVideoOpeningDiaogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getLifecycle().addObserver(binding.youtubePlayerView);
        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "fRLgHV-yu_U"
                youTubePlayer.loadVideo(videoId, 0f)
                binding.progressBar.visibility = View.GONE

            }
        })
    }

    override fun onStart() {
        super.onStart()
     /*   dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        val param = binding.root.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(35,10,35,10)
        binding.root.layoutParams = param*/

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setStyle(STYLE_NO_TITLE, android.R.style.ThemeOverlay_Material_Dialog)
    }

}