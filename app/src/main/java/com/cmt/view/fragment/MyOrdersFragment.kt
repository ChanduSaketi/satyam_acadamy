package com.cmt.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmt.R
import com.cmt.adapter.CategoriesAdapter
import com.cmt.adapter.MyOrdersAdapter
import com.cmt.databinding.FragmentMyOrdersBinding
import com.cmt.helper.IConstants
import com.cmt.services.model.HomeBannerModel
import com.cmt.services.model.MyOrdersModel
import com.cmt.viewModel.fragment.MyOrderVM


class MyOrdersFragment : Fragment() {
    private lateinit var binding: FragmentMyOrdersBinding
    var pageListCont: Int = 1
    var categoriesListAdapter: MyOrdersAdapter? = null
    var categoriesList: MutableList<MyOrdersModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMyOrdersBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@MyOrdersFragment).get(MyOrderVM::class.java)
            lifecycleOwner = this@MyOrdersFragment

        }
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*    binding.viewModel?.setOrdersData()
            binding.viewModel?.orderDetails?.observe(requireActivity(), {
                it?.let {
                    binding.recyclerView.apply {
                        adapter = MyOrdersAdapter(requireActivity(), it)

                    }

                }

            })*/

        setData()
        binding.viewModel?.setOrders(binding.recyclerView, pageListCont.toString())
        binding.viewModel?.orderDetails?.observe(requireActivity()) {
            if (it?.total_results_found != 0) {
                if (it != null) {
                    totalPagesFound = it.total_pages ?: 0
                    val listData = (it.results as MutableList<*>).filterIsInstance<MyOrdersModel>()
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
            } else {
                binding.noOrdersTv.visibility = View.VISIBLE
            }

        }
    }

    private fun setData() {
        categoriesListAdapter =
            MyOrdersAdapter(requireActivity(), categoriesList)
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
                    binding.viewModel?.setOrders(binding.recyclerView, pageListCont.toString())

                }
                isLoading = false
                isScrolling = false
                isLast = false
            }
        }

    }

}