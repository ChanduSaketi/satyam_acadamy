package com.cmt.view.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.cmt.R
import com.cmt.adapter.CategoriesAdapter
import com.cmt.adapter.MainSliderAdapter
import com.cmt.databinding.FragmentMainBinding
import com.cmt.helper.IConstants
import com.cmt.services.model.HomeBannerModel
import com.cmt.view.activity.PlainActivity
import com.cmt.viewModel.fragment.MainFragmentViewModel


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@MainFragment).get(MainFragmentViewModel::class.java)
            lifecycleOwner = this@MainFragment
        }
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.viewModel?.setBanners(view)
        binding.viewModel?.bannersData?.observe(requireActivity(), {
            if (it.size != 0) {
                it?.let {
                    binding.mainBanners.apply {
                        adapter = MainSliderAdapter(requireActivity(), it)
                        if (bannerHandler == null) {
                            autoScroll(7000)
                        }

                        clipToPadding = false
                        clipChildren = false
                        offscreenPageLimit = 3
                        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER


                        val comPosPageTarn = CompositePageTransformer()
                        comPosPageTarn.addTransformer(MarginPageTransformer(10))
                        comPosPageTarn.addTransformer { page, position ->
                            val r: Float = 1 - Math.abs(position)
                            page.scaleY = 0.85f + r * 0.15f
                        }
                        setPageTransformer(comPosPageTarn)

                    }


                }
            } else {
                binding.mainBanners.visibility = View.GONE
            }

        })



        binding.viewModel?.bottomBannersData?.observe(requireActivity(), {
            if (it.size != 0) {
                it?.let {
                    binding.bottomBanners.apply {
                        adapter = MainSliderAdapter(requireActivity(), it)
                        if (bannerHandler2 == null) {
                            autoScroll2(9000)
                        }

                        clipToPadding = false
                        clipChildren = false
                        offscreenPageLimit = 3
                        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

                    }

                }
            } else {
                binding.bottomBanners.visibility = View.GONE
            }

        })




        binding.viewModel?.setAdvBanners(requireActivity())
        binding.viewModel?.stickyBanners?.observe(requireActivity(), {
            it?.let {
                binding.stickyBanners.apply {
                    adapter = MainSliderAdapter(requireActivity(), it)
                }

            }
        })


        binding.viewModel?.sampCategoriesData?.observe(requireActivity(), {
            it?.let {
                binding.recyclerView.apply {
                    val listData =
                        (it.results as MutableList<*>).filterIsInstance<HomeBannerModel>()
                            .toMutableList()
                    adapter =
                        CategoriesAdapter(requireActivity(), listData, IConstants.Defaults.home)

                }

            }

        })

        binding.viewModel?.setCategories(view, "1")
    }


    //important for categories functionality in home screen
    /* binding.sub1Layout.setOnClickListener {
         val intent = Intent(requireActivity(), PlainActivity::class.java)
         intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.subCategories)
         intent.putExtra(IConstants.IntentStrings.payload, binding.category1.text)
         requireActivity().startActivity(intent)
         requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)

     }
     binding.sub2Layout.setOnClickListener {
         val intent = Intent(requireActivity(), PlainActivity::class.java)
         intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.subCategories)
         intent.putExtra(IConstants.IntentStrings.payload, binding.category2.text)
         requireActivity().startActivity(intent)
         requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)

     }*/


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
                val count = binding.mainBanners.adapter?.itemCount ?: 0
                scrollPosition = binding.mainBanners.currentItem
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

    private var bannerHandler2: Handler? = null
    private var bannerRunnable2: Runnable? = null

    private fun ViewPager2.autoScroll2(interval: Long) {

        bannerRunnable2?.let { runnable ->
            bannerHandler2?.removeCallbacks(runnable)
            bannerHandler2 = null
        }

        bannerHandler2 = Handler(Looper.getMainLooper())
        var scrollPosition: Int
        bannerRunnable2 = object : Runnable {
            override fun run() {
                val count = binding.bottomBanners.adapter?.itemCount ?: 0
                scrollPosition = binding.bottomBanners.currentItem
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
        bannerRunnable2?.let {
            bannerHandler2?.post(it)
        }

    }


}



