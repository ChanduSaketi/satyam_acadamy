package com.cmt.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cmt.R
import com.cmt.databinding.ActivityMainBinding
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.my_doctors.app.AppController
import com.cmt.view.fragment.ClearCartDialogFragment
import com.cmt.view.fragment.MainFragment
import com.cmt.viewModel.activity.MainActivityViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding
    var doubleBackToExitPressedOnce = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE);
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            viewModel = ViewModelProvider(this@MainActivity).get(MainActivityViewModel::class.java)
            lifecycleOwner = this@MainActivity
        }
        setContentView(binding.root)




        binding.viewModel?.model?.observe(this) {
            binding.customMenu.profileName.text = it.name
            binding.customMenu.mobileNum.text = it.mobile
            Glide.with(this)
                .load(it.image)
                .into(binding.customMenu.profilePic)


            /*if (it.image != null) {
                Glide.with(this)
                    .load(it.image)
                    .into(binding.customMenu.profilePic)
            } else {
                Glide.with(this)
                    .load(R.drawable.man)
                    .into(binding.customMenu.profilePic)
            }
*/
        }

        binding.drawLayout.setScrimColor(Color.TRANSPARENT)
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, binding.drawLayout, R.string.open_drawer,
            R.string.close_drawer
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
            }
        }

        binding.menuIcon.setOnClickListener {
            if (binding.drawLayout.isDrawerOpen(binding.navView) == false) {
                binding.drawLayout.openDrawer(binding.navView)
            } else {
                binding.drawLayout.closeDrawer(binding.navView)
            }
        }
        binding.drawLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()



        if (intent.hasExtra(IConstants.IntentStrings.type)) {
            when (intent.getStringExtra(IConstants.IntentStrings.type)) {
                IConstants.FragmentType.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFrameContainer, MainFragment())
                        .commit()
                }
            }
        }


        binding.customMenu.menuHome.setOnClickListener {
            binding.drawLayout.closeDrawer(binding.navView)
        }
        binding.customMenu.menuCategories.setOnClickListener {
            binding.drawLayout.closeDrawer(binding.navView)
            val intent = Intent(this, PlainActivity::class.java)
            intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.categeories)
            startActivity(intent)
            overridePendingTransition(R.anim.enter, R.anim.exit)
        }
        binding.customMenu.menuChangePassword.setOnClickListener {
            binding.drawLayout.closeDrawer(binding.navView)
            val intent = Intent(this, PlainActivity::class.java)
            intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.changePassword)
            startActivity(intent)
            overridePendingTransition(R.anim.enter, R.anim.exit)

        }
        binding.customMenu.menuCourses.setOnClickListener {
            binding.drawLayout.closeDrawer(binding.navView)
            val intent = Intent(this, PlainActivity::class.java)
            intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.myCourses)
            startActivity(intent)
            overridePendingTransition(R.anim.enter, R.anim.exit)
        }
        binding.customMenu.menuOrders.setOnClickListener {
            binding.drawLayout.closeDrawer(binding.navView)
            val intent = Intent(this, PlainActivity::class.java)
            intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.myOrders)
            startActivity(intent)
            overridePendingTransition(R.anim.enter, R.anim.exit)
        }
        binding.customMenu.menuMembership.setOnClickListener {
            binding.drawLayout.closeDrawer(binding.navView)
            val intent = Intent(this, PlainActivity::class.java)
            intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.membership)
            startActivity(intent)
            overridePendingTransition(R.anim.enter, R.anim.exit)
        }
        binding.customMenu.profile.setOnClickListener {
            val intent = Intent(this, PlainActivity::class.java)
            intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.myProfile)
            startActivity(intent)
            overridePendingTransition(R.anim.enter, R.anim.exit)
            binding.drawLayout.closeDrawer(binding.navView)

        }
        binding.customMenu.menuLogOut.setOnClickListener {
            binding.drawLayout.closeDrawer(binding.navView)
            AppController.dialogType = IConstants.Defaults.logout
            val dialog = ClearCartDialogFragment()
            dialog.show(supportFragmentManager, null)

        }

    }


    /* override fun onBackPressed() {
         val currentFragment = supportFragmentManager.findFragmentById(R.id.mainFrameContainer)
         if (currentFragment is MainFragment) {
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

    fun activityLoader(isShow: Boolean) {
        if (isShow) {
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            binding.mainFrameContainer.animate()?.alpha(0.2f)
            binding.progressBar.visibility = View.VISIBLE
        } else {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            binding.mainFrameContainer.animate()?.alpha(1f)
            binding.progressBar.visibility = View.GONE
        }
    }


    override fun onResume() {
        super.onResume()
        binding.viewModel?.setData(this)
        binding.viewModel?.deviceId(this)

    }


}
