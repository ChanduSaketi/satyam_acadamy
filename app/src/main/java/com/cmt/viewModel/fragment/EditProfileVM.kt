package com.cmt.viewModel.fragment

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.cmt.databinding.FragmentEditMyProfileBinding
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.hideKeyboard
import com.cmt.helper.setSnackBar
import com.cmt.services.api.Profile
import com.cmt.services.apiInterface.ProfileAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class EditProfileVM : ViewModel() {
    lateinit var binding: FragmentEditMyProfileBinding
    var imageFilePath: MutableLiveData<String> = MutableLiveData()
    var EditProfileModel: MutableLiveData<UserDetailsModel> = MutableLiveData()
    var uploadImageData: MutableLiveData<MutableList<String>> = MutableLiveData()
    private var imageFile: File? = null

    init {
        EditProfileModel.value = UserDetailsModel()
        uploadImageData.value = mutableListOf()

    }


    fun setData(model: UserDetailsModel) {
        EditProfileModel.value = model
    }

    fun setFile(context: Context, filePath: File) {
        val imageData = uploadImageData.value
        imageData?.add(filePath.toString())
        Glide.with(context).load(filePath).into(binding.profileImg)
        uploadImageData.value = imageData
        imageFile = filePath
        EditProfileModel.value?.image = uploadImageData.value.toString()
    }

    fun updateProfile(view: View) {
        updateProfile(view.context, imageFile)
    }

    private fun updateProfile(context: Context, filePath: File?) {
        val activity = context as? PlainActivity
        val params = HashMap<String, RequestBody>()
        activity?.activityLoader(true)
        params[IConstants.Params.name] = EditProfileModel.value?.name.toString()
            .toRequestBody("text/plain; charset=utf-8".toMediaType())
        params[IConstants.Params.email] = EditProfileModel.value?.email.toString()
            .toRequestBody("text/plain; charset=utf-8".toMediaType())
        params[IConstants.Params.mobile] = EditProfileModel.value?.mobile.toString()
            .toRequestBody("text/plain; charset=utf-8".toMediaType())
        params[IConstants.Params.user_id] = activity.let {
            AppPreferences().getUserId(it!!)
                .toRequestBody("text/plain; charset=utf-8".toMediaType())
        }

        Profile().updateProfile(params, filePath, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.status == IConstants.ServerValidate.valid) {
                        activity?.onBackPressed()
                        activity?.setSnackBar(apiResponse.message)
                        Toast.makeText(activity!!, "${apiResponse.message}", Toast.LENGTH_SHORT)
                            .show()

                    } else {
                        activity?.setSnackBar(apiResponse?.message)
                    }

                }
            }

        })
    }

}