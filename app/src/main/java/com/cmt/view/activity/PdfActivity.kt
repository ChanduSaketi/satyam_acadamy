package com.cmt.view.activity

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cmt.R
import com.cmt.databinding.ActivityPdfBinding
import com.cmt.helper.IConstants
import com.cmt.viewModel.activity.PdfActivityVM
import com.rajat.pdfviewer.PdfViewerActivity

class PdfActivity : AppCompatActivity() {
    lateinit var binding: ActivityPdfBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfBinding.inflate(layoutInflater).apply {
            viewModel = ViewModelProvider(this@PdfActivity).get(PdfActivityVM::class.java)
            lifecycleOwner = this@PdfActivity

        }
        setContentView(binding.root)

        binding.tvTitle.text = getString(R.string.title_material)
        val url = intent.getStringExtra(IConstants.IntentStrings.payload)

        startActivity(
            PdfViewerActivity.launchPdfFromUrl(           //PdfViewerActivity.Companion.launchPdfFromUrl(..   :: incase of JAVA
                this,
                url,                                // PDF URL in String format
                getString(R.string.title_material),                        // PDF Name/Title in String format
                "",                  // If nothing specific, Put "" it will save to Downloads
                enableDownload = false                    // This param is true by defualt.
            )
        )
        finish()

        /*binding.pdfContainer.id.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.pdfContainer, WebViewFragment(url))
                .commit()

        }*/
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }

    fun activityLoader(isShow: Boolean) {
        if (isShow) {
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            binding.pdfContainer.animate()?.alpha(0.2f)
            binding.progressBar.visibility = View.VISIBLE
        } else {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            binding.pdfContainer.animate()?.alpha(1f)
            binding.progressBar.visibility = View.GONE
        }
    }
}
