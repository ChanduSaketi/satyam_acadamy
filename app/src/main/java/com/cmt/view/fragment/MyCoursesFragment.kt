package com.cmt.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmt.adapter.CategoriesAdapter
import com.cmt.databinding.FragmentMyCoursesBinding
import com.cmt.helper.IConstants
import com.cmt.services.model.HomeBannerModel
import com.cmt.viewModel.fragment.MyCoursesVM

class MyCoursesFragment : Fragment() {
    lateinit var binding: FragmentMyCoursesBinding
    var pageListCont: Int = 1
    var categoriesListAdapter: CategoriesAdapter? = null
    var categoriesList: MutableList<HomeBannerModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMyCoursesBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@MyCoursesFragment).get(MyCoursesVM::class.java)
            lifecycleOwner = this@MyCoursesFragment
        }
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

/*        binding.viewModel?.setData(view)
        binding.viewModel?.coursesData?.observe(requireActivity(), {
            it?.let {
                binding.recyclerView.apply {
                    adapter = CategoriesAdapter(context, it, IConstants.Defaults.my_courses)

                }

            }

        })
    }*/

        setData()
        binding.viewModel?.setCourses(binding.recyclerView, pageListCont.toString())
        binding.viewModel?.coursesData?.observe(requireActivity()) {
            if (it?.total_results_found != 0) {
                if (it != null) {
                    totalPagesFound = it.total_pages ?: 0
                    val listData =
                        (it.results as MutableList<*>).filterIsInstance<HomeBannerModel>()
                            .toMutableList()
                    categoriesList.addAll(listData)
                    if (pageListCont == 1) {
                        categoriesListAdapter?.notifyItemRangeChanged(0, categoriesList.size)
                    } else {
                        val endPosition: Int = categoriesList.size
                        val additionalItemSize: Int = listData.size
                        val startPosition = endPosition - additionalItemSize
                        categoriesListAdapter?.notifyItemRangeChanged(startPosition, endPosition)
                    }
                } else {
                    categoriesList.clear()
                    categoriesListAdapter?.notifyDataSetChanged()

                }
            }else{
                binding.noCoursesTv.visibility = View.VISIBLE

            }

        }
    }

    private fun setData() {
        categoriesListAdapter =
            CategoriesAdapter(requireActivity(), categoriesList, IConstants.Defaults.my_courses)
        binding.recyclerView.apply {
            adapter = categoriesListAdapter
            addOnScrollListener(listScrollListener)
        }
    }


    //Pagination
    var isLoading = false
    var isScrolling = false
    var isLast = false
    var totalPagesFound = 0


    val listScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
            val firstViewItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLast
            val isAtLastItem = firstViewItemPosition + visibleItemCount >= totalItemCount
            val isNotBeginning = firstViewItemPosition >= 0
            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotBeginning && isScrolling

            if (shouldPaginate) {
                if (pageListCont < totalPagesFound) {
                    pageListCont += 1
                    binding.viewModel?.setCourses(binding.recyclerView, pageListCont.toString())

                }
                isLoading = false
                isScrolling = false
                isLast = false
            }
        }
    }

}