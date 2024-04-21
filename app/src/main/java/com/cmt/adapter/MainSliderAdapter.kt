package com.cmt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmt.databinding.LayoutMainSliderBinding
import com.cmt.services.model.BannerOneModel
import com.cmt.services.model.HomeBannerModel

class MainSliderAdapter(val context: Context, val dataset: MutableList<BannerOneModel>) :
    RecyclerView.Adapter<MainSliderAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: LayoutMainSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(dataModel: BannerOneModel) {
            binding.model = dataModel
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutMainSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}