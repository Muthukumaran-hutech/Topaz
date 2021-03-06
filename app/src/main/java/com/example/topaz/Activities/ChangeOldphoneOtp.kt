package com.example.topaz.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.topaz.ApiModels.ChangeOldPhoneOtpApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityChangeOldEmailOtpBinding
import com.example.topaz.databinding.ActivityChangeOldphoneOtpBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeOldphoneOtp : AppCompatActivity() {
    lateinit var activity: Activity
    private lateinit var binding: ActivityChangeOldphoneOtpBinding
    var custId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeOldphoneOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        binding.resendOtp2.setOnClickListener {
            checkUserApiCall(custId)
            countdownTimer()
        }


        val sharedPreference =  getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId= sharedPreference.getString("customercode","").toString()
        var custPhoneno = sharedPreference.getString("primaryPhonenumber","")


        binding.confirmPhoneOtp.setOnClickListener {

            if (binding.otp01.text.toString().trim().isEmpty()
                || binding.otp02.text.toString().trim().isEmpty()
                || binding.otp03.text.toString().trim().isEmpty()
                || binding.otp04.text.toString().trim().isEmpty()
                || binding.otp05.text.toString().trim().isEmpty()
                || binding.otp06.text.toString().trim().isEmpty()
            ) {

                Toast.makeText(applicationContext, "Please Enter The Valid OTP", Toast.LENGTH_LONG)
                    .show()
            } else {
                binding.appProgressBar.visibility = View.VISIBLE
                binding.appProgressBar.visibility = View.VISIBLE

                checkUserApiCall(custId)

            }

        }

        countdownTimer()

        binding.otp01.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.otp02.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp02.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.otp03.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp03.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.otp04.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp04.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.otp05.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp05.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.otp06.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp06.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.confirmPhoneOtp.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })



    }

    private fun countdownTimer() {
        object : CountDownTimer(120000, 1000) {

            // Callback function, fired on regular interval
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                if (seconds < 10) {
                    binding.timer.text =
                        ("0" + minutes.toString() + ":" + "0" + seconds.toString())
                } else {
                    binding.timer.text =
                        ("0" + minutes.toString() + ":" + seconds.toString())
                }
                binding.timer.visibility = View.VISIBLE
                binding.dintRecieve.visibility = View.GONE
                binding.resendOtp2.visibility = View.GONE

                /* if (minutes <=1){
                     binding.countdownTimer.text = ("0"+minutes.toString() + ":" + seconds.toString())
                 }
                 else{
                     binding.countdownTimer.text = (minutes.toString() + ":" + seconds.toString())
                 }*/

            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                binding.timer.text = binding.dintRecieve.toString()
                binding.dintRecieve.visibility = View.VISIBLE
                binding.resendOtp2.visibility = View.VISIBLE
                binding.appProgressBar.visibility = View.GONE
                binding.timer.visibility = View.GONE
            }
        }.start()


    }

    private fun checkUserApiCall(custId : String) {
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)



        val recievedOtp = binding.otp01.text.toString() + binding.otp02.text.toString() +
                binding.otp03.text.toString() + binding.otp04.text.toString() +
                binding.otp05.text.toString() + binding.otp06.text.toString()

        val body: RequestBody = recievedOtp.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
        requestBodyMap["phoneotp"] = body

        res.verifyoldPhoneOtp(custId,body).enqueue(object : Callback<ChangeOldPhoneOtpApiModel?> {
            override fun onResponse(
                call: Call<ChangeOldPhoneOtpApiModel?>,
                response: Response<ChangeOldPhoneOtpApiModel?>
            ) {
                if (response.isSuccessful){
                    Log.d(ContentValues.TAG, "onResponse succes phone: "+ response.body().toString())
                    startActivity(Intent(activity, ChangePhoneNo::class.java))
                    finish()
                }else{
                    Log.d(ContentValues.TAG, "onResponse succes phone: "+ response.body().toString())
                }
            }

            override fun onFailure(call: Call<ChangeOldPhoneOtpApiModel?>, t: Throwable) {
                Log.d(ContentValues.TAG, "onResponse succes phone: "+ t.message)
            }
        })

    }
}