package com.example.topaz.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.example.topaz.ApiModels.ChangeNewEmailOtpApiModel
import com.example.topaz.ApiModels.VerifyNewEmailApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.ProductDetailsModel
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityChangeOldEmailBinding
import com.example.topaz.databinding.ActivityEmailChangeOtpBinding
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_change_email.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

private lateinit var binding: ActivityEmailChangeOtpBinding

class EmailChangeOtp : AppCompatActivity() {
    lateinit var activity: Activity
    var emailChange = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailChangeOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this

        val item = intent.getStringExtra("extra_email")
        if (item != null) {
            emailChange =item
        }

        binding.resendOtp2.setOnClickListener {
            countdownTimer()
            resendNewEmailChangeOTP()
        }

        binding.otp001.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if(s.toString().length==1){
                    binding.otp002.requestFocus()
                }
                else{
                    binding.otp001.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // binding.otp02.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp002.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length==1){
                    binding.otp003.requestFocus()
                }
                else{
                    binding.otp002.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //binding.otp03.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp003.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length==1){
                    binding.otp004.requestFocus()
                }
                else{
                    binding.otp003.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //binding.otp04.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp004.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length==1){
                    binding.otp005.requestFocus()
                }
                else{
                    binding.otp004.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // binding.otp05.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp005.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length==1){
                    binding.otp006.requestFocus()
                }
                else{
                    binding.otp005.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //binding.otp06.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp006.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.confirmEmailOtp2.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        //Handling delete functionality
        binding.otp002.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_DEL){
                    if(binding.otp002.text.toString().isEmpty()) {
                        binding.otp001.requestFocus()
                    }
                }

                return false
            }
        })
        binding.otp003.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_DEL){
                    if(binding.otp003.text.toString().isEmpty()) {
                        binding.otp002.requestFocus()
                    }
                }

                return false
            }
        })
        binding.otp004.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_DEL){
                    if(binding.otp004.text.toString().isEmpty()) {
                        binding.otp003.requestFocus()
                    }
                }

                return false
            }
        })
        binding.otp005.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_DEL){

                    if(binding.otp005.text.toString().isEmpty()) {
                        binding.otp004.requestFocus()
                    }

                }

                return false
            }
        })
        binding.otp006.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_DEL){

                    if(binding.otp006.text.toString().isEmpty()) {
                        binding.otp005.requestFocus()
                    }
                }

                return false
            }
        })



        binding.confirmEmailOtp2.setOnClickListener {

            if (binding.otp001.text.toString().trim().isEmpty()
                || binding.otp002.text.toString().trim().isEmpty()
                || binding.otp003.text.toString().trim().isEmpty()
                || binding.otp004.text.toString().trim().isEmpty()
                || binding.otp005.text.toString().trim().isEmpty()
                || binding.otp006.text.toString().trim().isEmpty()
            ) {

                Toast.makeText(applicationContext, "Please Enter The Valid OTP", Toast.LENGTH_LONG)
                    .show()
            } else {
                binding.appProgressBar.visibility = View.VISIBLE
                binding.appProgressBar.visibility = View.VISIBLE
                checkUserApiCall()
            }

        }
        countdownTimer()
    }

    private fun checkUserApiCall() {
        val res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)


        val recievedOtp = binding.otp001.text.toString() + binding.otp002.text.toString() +
                binding.otp003.text.toString() + binding.otp004.text.toString() +
                binding.otp005.text.toString() + binding.otp006.text.toString()

        val body: RequestBody = recievedOtp.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
        requestBodyMap["emailOtp"] = body

        res.verifyNewEmailOtp(requestBody = body)
            .enqueue(object : Callback<ChangeNewEmailOtpApiModel?> {
                override fun onResponse(
                    call: Call<ChangeNewEmailOtpApiModel?>,
                    response: Response<ChangeNewEmailOtpApiModel?>
                ) {
                    if (response.isSuccessful) {
                        val sharedPreference =  getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
                        val editor = sharedPreference.edit()
                        editor.putString("email",emailChange).apply()
                        Log.d(TAG, "onResponse: Success " + response.body().toString())
                        startActivity(Intent(activity, EditProfile::class.java))
                        finish()
                        Toast.makeText(
                            applicationContext,
                            "Email Verified Sucessfully",
                            Toast.LENGTH_LONG
                        )
                            .show()


                    } else {
                        Log.d(TAG, "onResponse: Fail " + response.body().toString())
                        Toast.makeText(
                            applicationContext,
                            "Something Went Wrong User Details Not updated",
                            Toast.LENGTH_LONG
                        )
                            .show()

                    }
                }

                override fun onFailure(call: Call<ChangeNewEmailOtpApiModel?>, t: Throwable) {
                    Log.d(TAG, "onResponse: Failure " + t.message)
                    Toast.makeText(
                        applicationContext,
                        "Something Went Wrong User Details Not updated",
                        Toast.LENGTH_LONG
                    )
                        .show()

                }
            })


    }

    private fun signInWithPhoneAuthCredential(otpCode: String) {

    }


    private fun resendNewEmailChangeOTP() {
        try {
            binding.appProgressBar.visibility = View.VISIBLE
            var resendNewEmailOtpRes = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                .create(JsonPlaceholder::class.java)

            val body: RequestBody =
                emailChange.toRequestBody("text/plain".toMediaTypeOrNull())
            val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
            requestBodyMap["email"] = body

            resendNewEmailOtpRes.verifyNewEmail(emailChange,body).enqueue(object : Callback<VerifyNewEmailApiModel?> {
                override fun onResponse(
                    call: Call<VerifyNewEmailApiModel?>,
                    response: Response<VerifyNewEmailApiModel?>
                ) {
                    binding.appProgressBar.visibility = View.GONE
                    if(response.isSuccessful){
                        Toast.makeText(this@EmailChangeOtp,"OTP sent to entered emailId",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this@EmailChangeOtp,"Something went wrong",Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<VerifyNewEmailApiModel?>, t: Throwable) {
                    Toast.makeText(this@EmailChangeOtp,"Something went wrong",Toast.LENGTH_LONG).show()
                    binding.appProgressBar.visibility = View.GONE
                }
            })
        }
        catch (e:Exception){
            e.toString()
            binding.appProgressBar.visibility = View.GONE
        }


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
}