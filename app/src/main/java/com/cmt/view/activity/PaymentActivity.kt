package com.cmt.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cmt.databinding.ActivityPaymentBinding
import com.cmt.helper.IConstants
import com.cmt.my_doctors.app.AppController
import com.cmt.services.model.CheckOutModel
import com.cmt.viewModel.fragment.PaymentVM
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(), PaymentResultWithDataListener {
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var checkout: Checkout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater).apply {
            viewModel = ViewModelProvider(this@PaymentActivity).get(PaymentVM::class.java)
            lifecycleOwner = this@PaymentActivity

        }
        setContentView(binding.root)

        if (AppController.paymentThrough == IConstants.Defaults.subscription) {
            val price = intent.getStringExtra(IConstants.IntentStrings.price)
            binding.viewModel?.paymentProcess(binding.progressBar, null, price)

        } else {
            val model = intent.getSerializableExtra(IConstants.IntentStrings.payload) as? CheckOutModel
            binding.viewModel?.paymentProcess(binding.progressBar, model!!)

        }


        binding.viewModel?.paymentObject?.observe(this) {
            it?.let {
                startRazorPayPayment(it)

            }

        }

    }

    private fun startRazorPayPayment(jsonObject: JSONObject) {
        Checkout.preload(applicationContext)
        checkout = Checkout()
        checkout.setKeyID(jsonObject.getString("key"))
        jsonObject.getJSONObject("meta_info").put("send_sms_hash", false)

        checkout.open(this, jsonObject.getJSONObject("meta_info"))

    }


    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {

        binding.viewModel?.paymentSuccess(this, p1)

    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        /* try {
             val jsonObject = JSONObject(p1)
             val error = jsonObject.getJSONObject("error")
             Toast.makeText(this, error.getString("description"), Toast.LENGTH_LONG).show()
         } catch (e: Exception) {
             e.printStackTrace()
         }
 */
        binding.viewModel?.paymentFailure(this, p2)


    }


}