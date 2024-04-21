package com.cmt.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.view.fragment.PurchaseSuccessfulFragment
import com.cmt.R
import com.cmt.databinding.ActivityPlainBinding
import com.cmt.helper.IConstants
import com.cmt.services.model.CheckOutModel
import com.cmt.services.model.HomeBannerModel
import com.cmt.services.model.MyOrdersModel
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.fragment.*
import com.cmt.viewModel.activity.PlainViewModel
import com.cmt.view.fragment.OrderDetailsFragment

class PlainActivity : BaseActivity() {
    private lateinit var binding: ActivityPlainBinding
    var searchType: String? = null
    var doubleBackToExitPressedOnce = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE);
        binding = ActivityPlainBinding.inflate(layoutInflater)
            .apply {
                viewModel = ViewModelProvider(this@PlainActivity).get(PlainViewModel::class.java)
                lifecycleOwner = this@PlainActivity
            }
        setContentView(binding.root)

        /* binding.editIcon.setOnClickListener {
             val intent = Intent(this, PlainActivity::class.java)
             intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.editProfile)
             startActivity(intent)
             overridePendingTransition(R.anim.enter, R.anim.exit)
         }
 */

        val currentFragment = supportFragmentManager.findFragmentById(R.id.plainContainer)

        if(currentFragment == CategoriesFragment()){
            Toast.makeText(this, "Categories", Toast.LENGTH_SHORT).show()
        }

        if (intent.hasExtra(IConstants.IntentStrings.type)) {
            when (intent.getStringExtra(IConstants.IntentStrings.type)) {
                IConstants.FragmentType.login -> {
                    binding.backNav.visibility = View.GONE
                    loadFragment(LoginFragment())
                }
                IConstants.FragmentType.register -> {
                    loadFragment(RegisterFragment())
                }
                IConstants.FragmentType.otp -> {
                    val mobileNumber = intent.getStringExtra(IConstants.IntentStrings.payload)
                    loadFragment(OtpFragment(mobileNumber))

                }
                IConstants.FragmentType.subscirbePlan -> {
                    binding.title.visibility = View.VISIBLE
                    binding.title.text = "Subscribe Plans"
                    val renewalAmount = intent.getStringExtra(IConstants.IntentStrings.payload)
                    loadFragment(SubscribePlansFragment(renewalAmount))

                }
                IConstants.FragmentType.cart -> {
                    binding.title.visibility = View.VISIBLE
                    binding.title.text = "Checkout"
                    val model =
                        intent.getSerializableExtra(IConstants.IntentStrings.payload) as? CheckOutModel
                    loadFragment(CartFragment(model!!))

                }
                IConstants.FragmentType.categeories -> {
                    binding.title.visibility = View.VISIBLE
                    binding.title.text = "Categories"
                    loadFragment(CategoriesFragment())
                }
                /* IConstants.FragmentType.videosPlay -> {
                     binding.toolBar.visibility = View.GONE
                     loadFragment(VideoPlayingFragment(intent.getStringExtra(IConstants.IntentStrings.payload)))
                 }*/
                IConstants.FragmentType.myProfile -> {
                    binding.title.visibility = View.VISIBLE
                    binding.editIcon.visibility = View.VISIBLE
                    binding.title.text = "My Profile"
                    loadFragment(MyProfileFragment())
                }

                IConstants.FragmentType.subCategories -> {
                    binding.title.visibility = View.VISIBLE
                    binding.coursePrice.visibility = View.VISIBLE
                    val model =
                        intent.getSerializableExtra(IConstants.IntentStrings.payload) as? HomeBannerModel
                    binding.title.text = model?.name
                    binding.coursePrice.text = "₹" + model?.price
                    loadFragment(SubCategoryFragment(model!!))
                }
                IConstants.FragmentType.membership -> {
                    binding.title.visibility = View.VISIBLE
                    binding.title.text = "My Membership"
                    loadFragment(MembershipFragment())
                }
                IConstants.FragmentType.editProfile -> {
                    binding.title.visibility = View.VISIBLE
                    binding.title.text = "Edit Profile"
                    val model =
                        intent.getSerializableExtra(IConstants.IntentStrings.payload) as? UserDetailsModel
                    loadFragment(EditMyProfileFragment(model!!))
                }
                IConstants.FragmentType.myOrders -> {
                    binding.title.visibility = View.VISIBLE
                    binding.title.text = "My Orders"
                    loadFragment(MyOrdersFragment())
                }
                IConstants.FragmentType.myCoursesDetails -> {
                    binding.title.visibility = View.VISIBLE
                    val model =
                        intent.getSerializableExtra(IConstants.IntentStrings.payload) as? HomeBannerModel
                    binding.title.text = model?.name
                    loadFragment(MyCourseDetailsFragment(model!!))
                }
                IConstants.FragmentType.myCourses -> {
                    binding.title.visibility = View.VISIBLE
                    binding.title.text = "My Course"
                    loadFragment(MyCoursesFragment())
                }
                IConstants.FragmentType.changePassword -> {
                    binding.title.visibility = View.VISIBLE
                    binding.title.text = "Change Password"
                    loadFragment(ChangePasswordFragment())
                }
                IConstants.FragmentType.forgotPassword -> {
                    binding.toolBar.visibility = View.GONE
                    loadFragment(ForgotPasswordFragment())
                }
                IConstants.FragmentType.chooseNewPassword -> {
                    binding.title.visibility = View.VISIBLE
                    binding.title.text = "Choose New Password"
                    loadFragment(ChooseNewPasswordFragment())
                }
                IConstants.FragmentType.purchased_successful -> {
                    binding.toolBar.visibility = View.GONE
                    loadFragment(PurchaseSuccessfulFragment())
                }
                IConstants.FragmentType.order_details -> {
                    binding.title.visibility = View.VISIBLE
                    val model =
                        intent.getSerializableExtra(IConstants.IntentStrings.payload) as? MyOrdersModel
                    binding.title.text = "#" + model?.order_id
                    loadFragment(OrderDetailsFragment(model!!))
                }

            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit)
    }


    /*  override fun onBackPressed() {
          val currentFragment = supportFragmentManager.findFragmentById(R.id.plainContainer)
          if (currentFragment is LoginFragment) {
              if (doubleBackToExitPressedOnce) {
                  val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
                  alertDialog.setMessage("Are You Really Want To Exit")
                  alertDialog.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                      override fun onClick(p0: DialogInterface?, p1: Int) {
                          finishAffinity()
                      }
                  })
                  alertDialog.setNegativeButton("No", object : DialogInterface.OnClickListener {
                      override fun onClick(dialogInterface: DialogInterface?, p1: Int) {

                          dialogInterface?.dismiss()
                      }
                  })

                  alertDialog.show().create()

                  return
              }
              this.doubleBackToExitPressedOnce = true
              setSnackBar("Press again to exit")
              Handler(Looper.getMainLooper()).postDelayed(
                  { doubleBackToExitPressedOnce = false },
                  2000
              )
          } else {
              super.onBackPressed()
          }
      }*/


    private fun loadFragment(fragment: Fragment) {
        binding.plainContainer.id.let {
            supportFragmentManager.beginTransaction()
                .replace(it, fragment).commit()
        }
    }

    fun activityLoader(isShow: Boolean) {
        if (isShow) {
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            binding.plainContainer.animate()?.alpha(0.2f)
            binding.progressBar.visibility = View.VISIBLE
        } else {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            binding.plainContainer.animate()?.alpha(1f)
            binding.progressBar.visibility = View.GONE
        }
    }
}
