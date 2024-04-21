package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cmt.R
import com.cmt.databinding.LayoutCategoriesBinding
import com.cmt.databinding.LayoutMaterialsBinding
import com.cmt.helper.IConstants
import com.cmt.services.model.HomeBannerModel
import com.cmt.view.activity.PdfActivity
import com.cmt.view.activity.PlainActivity
import com.rajat.pdfviewer.PdfViewerActivity

class MaterialsAdapter(val context: Context, val dataset: MutableList<HomeBannerModel>) :
    RecyclerView.Adapter<MaterialsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: LayoutMaterialsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(dataModel: HomeBannerModel) {
            binding.model = dataModel

            binding.root.setOnClickListener {
                val activity = context as? PlainActivity

                /*  ContextCompat.startActivity(
                      context,
                      PdfViewerActivity.launchPdfFromUrl(           //PdfViewerActivity.Companion.launchPdfFromUrl(..   :: incase of JAVA
                          context,
                         "https://www.clickdimensions.com/links/TestPDFfile.pdf",                                // PDF URL in String format
                          dataModel.name,                        // PDF Name/Title in String format
                          "",                  // If nothing specific, Put "" it will save to Downloads
                          enableDownload = false                    // This param is true by defualt.
                      ), null
                  )*/

                ContextCompat.startActivity(
                context,
                PdfViewerActivity.launchPdfFromUrl(           //PdfViewerActivity.Companion.launchPdfFromUrl(..   :: incase of JAVA
                    context,
                    dataModel.book_url,                                // PDF URL in String format
                    dataModel.name,                        // PDF Name/Title in String format
                    "",                  // If nothing specific, Put "" it will save to Downloads
                    enableDownload = false                    // This param is true by defualt.
                ), null
            )
                activity?.overridePendingTransition(R.anim.enter, R.anim.exit)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutMaterialsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}