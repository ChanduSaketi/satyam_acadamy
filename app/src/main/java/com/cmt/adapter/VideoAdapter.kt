package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmt.R
import com.cmt.databinding.VideoViewBinding
import com.cmt.helper.IConstants
import com.cmt.services.model.VideoModel
import com.cmt.view.activity.PlainActivity
import com.cmt.view.activity.VideoPlayingActivity

class VideoAdapter(val context: Context, val dataSet: MutableList<VideoModel>) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: VideoViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(dataModel: VideoModel) {
            binding.model = dataModel
            binding.playIcon.setOnClickListener {
                val intent = Intent(context, VideoPlayingActivity::class.java)
                intent.putExtra(IConstants.IntentStrings.payload, dataModel.video_id)
                context.startActivity(intent)
                (context as PlainActivity).overridePendingTransition(R.anim.enter, R.anim.exit)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VideoViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}