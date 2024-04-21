package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cmt.R
import com.cmt.databinding.LayoutMyOrdersBinding
import com.cmt.helper.IConstants
import com.cmt.services.model.MyOrdersModel
import com.cmt.view.activity.PlainActivity

class MyOrdersAdapter(val context: Context, val dataset: MutableList<MyOrdersModel>) :
    RecyclerView.Adapter<MyOrdersAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: LayoutMyOrdersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(dataModel: MyOrdersModel) {
            binding.model = dataModel
            binding.root.setOnClickListener {
                val intent = Intent(context,PlainActivity::class.java)
                intent.putExtra(IConstants.IntentStrings.type,IConstants.FragmentType.order_details)
                intent.putExtra(IConstants.IntentStrings.payload,dataModel)
                context.startActivity(intent)
                (context as FragmentActivity).overridePendingTransition(R.anim.enter,R.anim.exit)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutMyOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}