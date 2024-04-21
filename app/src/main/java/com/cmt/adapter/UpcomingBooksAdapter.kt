package com.cmt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmt.databinding.LayoutUpcomingBooksBinding
import com.cmt.services.model.HomeBannerModel

class UpcomingBooksAdapter(val context: Context, val dataSet: MutableList<HomeBannerModel>) :
    RecyclerView.Adapter<UpcomingBooksAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: LayoutUpcomingBooksBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(dataModel: HomeBannerModel) {
            binding.model = dataModel
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutUpcomingBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}