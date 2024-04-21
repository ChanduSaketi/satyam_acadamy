package com.cmt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.MaterialsAdapter
import com.cmt.adapter.VideoAdapter
import com.cmt.databinding.FragmentMyCourseDetailsBinding
import com.cmt.services.model.HomeBannerModel
import com.cmt.viewModel.fragment.MyCoursesDetailsVM


class MyCourseDetailsFragment(val model: HomeBannerModel? = null) : Fragment() {
    private lateinit var binding: FragmentMyCourseDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMyCourseDetailsBinding.inflate(layoutInflater, container, false).apply {
            viewModel =
                ViewModelProvider(this@MyCourseDetailsFragment).get(MyCoursesDetailsVM::class.java)
            lifecycleOwner = this@MyCourseDetailsFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*    binding.materialLayout.setOnClickListener {
                val activity = requireActivity() as? PlainActivity
                ContextCompat.startActivity(
                    requireActivity(),
                    PdfViewerActivity.launchPdfFromUrl(           //PdfViewerActivity.Companion.launchPdfFromUrl(..   :: incase of JAVA
                        context,
                        "https://www.clickdimensions.com/links/TestPDFfile.pdf",                                // PDF URL in String format
                        binding.materialName.text.toString(),                        // PDF Name/Title in String format
                        "",                  // If nothing specific, Put "" it will save to Downloads
                        enableDownload = false                    // This param is true by defualt.
                    ), null
                )
                activity?.overridePendingTransition(R.anim.enter, R.anim.exit)

            }*/
        if (model?.is_subscribed == 0) {
            binding.viewModel?.subscriptionMessage?.value = true
        } else {
            binding.viewModel?.subscriptionMessage?.value = false
        }
        binding.viewModel?.description?.value = model?.description
        binding.viewModel?.courseName?.value = model?.name


        binding.viewModel?.setPdfData(view, model?.category_id!!)
        binding.viewModel?.pdfData?.observe(requireActivity(), {
            if (it?.size != 0) {
                it?.let {
                    binding.pdfRecyclerView.apply {
                        adapter = MaterialsAdapter(requireActivity(), it)
                    }
                }

            } else {
                binding.noMaterialsTv.visibility = View.VISIBLE
            }
        })

        binding.viewModel?.setThumbnails(view, model?.category_id!!)
        binding.viewModel?.videosThumbnailsData?.observe(requireActivity(), {
            if (it?.size != 0) {
                it?.let {
                    binding.videosRecycler.apply {
                        adapter = VideoAdapter(requireActivity(), it)

                    }

                }
            }else{
                binding.noVideosTv.visibility = View.VISIBLE
            }

        })


    }
}
