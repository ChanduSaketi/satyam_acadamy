package com.cmt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.MaterialsAdapter
import com.cmt.adapter.UpcomingBooksAdapter
import com.cmt.databinding.FragmentBooksBinding
import com.cmt.helper.ICallback
import com.cmt.services.model.HomeBannerModel
import com.cmt.viewModel.fragment.BooksVM

class BooksFragment(val model: HomeBannerModel? = null, val mCallBack: ICallback? = null) :
    Fragment() {
    private lateinit var binding: FragmentBooksBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBooksBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@BooksFragment).get(BooksVM::class.java)
            lifecycleOwner = this@BooksFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel?.setPdfData(view, model!!)
        binding.viewModel?.pdfData?.observe(requireActivity()) {
            if (it?.size != 0) {
                mCallBack?.delegate(true)
                it?.let {
                    binding.materialRecycler.apply {
                        adapter = MaterialsAdapter(requireActivity(), it)
                        mCallBack?.delegate(binding.viewModel?.pdfData)


                    }


                }

            } else {
                binding.noMaterialsTv.visibility = View.VISIBLE
                mCallBack?.delegate(false)

            }

        }

        //upcoming books list

        binding.viewModel?.setUpcomingBooks(view, model!!)
        binding.viewModel?.upcomingBooks?.observe(requireActivity()) {
            if (it?.size != 0) {
                it?.let {
                    binding.upcomingBooksRecycler.apply {
                        adapter = UpcomingBooksAdapter(requireActivity(), it)
                    }

                }

            } else {
                binding.upComingBoooks.visibility = View.GONE
            }

        }
/*
        if(booksList==false && videosList == false){
            binding.viewModel?.isDataAvailable?.value = false
        }*/


    }


}