package com.cmt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.VideoAdapter
import com.cmt.databinding.FragmentVideosBinding
import com.cmt.helper.ICallback
import com.cmt.services.model.HomeBannerModel
import com.cmt.view.youTubeVideos
import com.cmt.viewModel.fragment.VideosVM
import java.util.*


class VideosFragment(val model: HomeBannerModel? = null,val mCallBack : ICallback?= null) : Fragment() {
    private lateinit var binding: FragmentVideosBinding
    var youtubeVideos = Vector<youTubeVideos>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentVideosBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@VideosFragment).get(VideosVM::class.java)
            lifecycleOwner = this@VideosFragment
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel?.setThumbnails(view, model!!)
        binding.viewModel?.videosThumbnailsData?.observe(requireActivity()) {
            if (it?.size != 0) {
                mCallBack?.delegate(true)
                it?.let {
                    binding.videosRecycler.apply {
                        adapter = VideoAdapter(requireActivity(), it)

                    }

                }
            } else {
                binding.noVideosTv.visibility = View.VISIBLE
                mCallBack?.delegate(false)

            }


        }
    }

}