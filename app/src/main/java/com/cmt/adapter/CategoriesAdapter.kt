package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cmt.R
import com.cmt.databinding.LayoutCategoriesBinding
import com.cmt.helper.IConstants
import com.cmt.services.model.HomeBannerModel
import com.cmt.view.activity.PlainActivity

class CategoriesAdapter(
    val context: Context,
    val dataset: MutableList<HomeBannerModel>,
    var type: String? = null,
) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: LayoutCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(dataModel: HomeBannerModel) {
            binding.model = dataModel
          /*  if(type == IConstants.Defaults.home){
                if (dataset.size <= 6)
                  dataset.size
                else
                    6
            }*/
            binding.root.setOnClickListener {
                if (type == IConstants.Defaults.my_courses) {
                    val intent = Intent(context, PlainActivity::class.java)
                    intent.putExtra(IConstants.IntentStrings.type,
                        IConstants.FragmentType.myCoursesDetails)
                    intent.putExtra(IConstants.IntentStrings.payload, dataModel)
                    intent.putExtra(IConstants.IntentStrings.category_id, dataModel.category_id ?: "")
                    context.startActivity(intent)
                    (context as FragmentActivity).overridePendingTransition(R.anim.enter,
                        R.anim.exit)
                } else {
                    val intent = Intent(context, PlainActivity::class.java)
                    intent.putExtra(
                        IConstants.IntentStrings.type,
                        IConstants.FragmentType.subCategories
                    )
                    intent.putExtra(IConstants.IntentStrings.payload, dataModel)
                    context.startActivity(intent)
                    (context as FragmentActivity).overridePendingTransition(R.anim.enter,
                        R.anim.exit)

                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}