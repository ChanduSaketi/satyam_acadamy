package com.cmt.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.cmt.R
import com.cmt.adapter.MainSliderAdapter
import com.cmt.databinding.FragmentSubCategoryBinding
import com.cmt.helper.ICallback
import com.cmt.services.model.HomeBannerModel
import com.cmt.view.activity.PlainActivity
import com.cmt.viewModel.fragment.SubCategoryVM
import com.google.android.material.tabs.TabLayoutMediator


class SubCategoryFragment(val model: HomeBannerModel? = null) : Fragment() {
    private lateinit var binding: FragmentSubCategoryBinding
    var isBooksAvailable: Boolean? = false
    var isVideosAvailable: Boolean? = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSubCategoryBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@SubCategoryFragment).get(SubCategoryVM::class.java)
            lifecycleOwner = this@SubCategoryFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (requireActivity() as? PlainActivity)?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.subCategoriesFrameContainer, BooksFragment(model!!, object : ICallback {
                override fun delegate(any: Any?) {
                    if (any != null) {
                        isBooksAvailable = any as? Boolean
                        Toast.makeText(requireActivity(), "$any", Toast.LENGTH_SHORT).show()

                    }
                }

            }))
            ?.commit()

        binding.rbBooks.setOnClickListener {
            (requireActivity() as? PlainActivity)?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.subCategoriesFrameContainer,
                    BooksFragment(model!!, object : ICallback {
                        override fun delegate(any: Any?) {
                            if (any != null) {
                                isBooksAvailable = any as? Boolean

                            }
                        }

                    }))
                ?.commit()
        }

        binding.rbVideos.setOnClickListener {
            (requireActivity() as? PlainActivity)?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.subCategoriesFrameContainer,
                    VideosFragment(model!!, object : ICallback {
                        override fun delegate(any: Any?) {
                            if (any != null) {
                                if (any is Boolean) {
                                    isVideosAvailable = any
                                }
                            }
                        }

                    }))
                ?.commit()
        }


        binding.viewModel?.checkOut(view, model!!)
        binding.viewModel?.checkPurchasedCourse(view)

        binding.viewModel?.setSliders(view, model!!)
        binding.viewModel?.sliderData?.observe(requireActivity()) {
            if (it.size != 0) {
                it?.let {
                    binding.sliders.apply {
                        adapter = MainSliderAdapter(requireActivity(), it)

                        if (bannerHandler == null) {
                            autoScroll(7000)
                        }
                        isUserInputEnabled = true

                        clipToPadding = false
                        clipChildren = false
                        offscreenPageLimit = 3
                        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER


                        val comPosPageTarn = CompositePageTransformer()
                        comPosPageTarn.addTransformer(MarginPageTransformer(20))
                        comPosPageTarn.addTransformer { page, position ->
                            val r: Float = 1 - Math.abs(position)
                            page.scaleY = 0.85f + r * 0.15f
                        }
                        setPageTransformer(comPosPageTarn)

                    }

                    TabLayoutMediator(binding.viewPagerDots, binding.sliders) { tab, position ->

                    }.attach()

                }

            } else {
                binding.sliders.visibility = View.GONE
                binding.viewPagerDots.visibility = View.GONE
            }

        }

        if (isBooksAvailable == false && isVideosAvailable == false) {
            binding.viewModel?.isDataAvailable?.value = false
//            Toast.makeText(requireActivity(), "${binding.viewModel?.isDataAvailable?.value}", Toast.LENGTH_SHORT).show()
        }

    }

    private var bannerHandler: Handler? = null
    private var bannerRunnable: Runnable? = null

    private fun ViewPager2.autoScroll(interval: Long) {

        bannerRunnable?.let { runnable ->
            bannerHandler?.removeCallbacks(runnable)
            bannerHandler = null
        }

        bannerHandler = Handler(Looper.getMainLooper())
        var scrollPosition: Int
        bannerRunnable = object : Runnable {
            override fun run() {
                val count = binding.sliders.adapter?.itemCount ?: 0
                scrollPosition = binding.sliders.currentItem
                if (scrollPosition < count - 1) {
                    scrollPosition += 1
                    setCurrentItem(scrollPosition, true)
                } else {
                    scrollPosition = 0
                    setCurrentItem(scrollPosition, true)
                }
                postDelayed(this, interval)
            }
        }
        bannerRunnable?.let {
            bannerHandler?.post(it)
        }
    }


}