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
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.example.topaz.ApiModels.ChangeEmailOtpApiModel
import com.example.topaz.ApiModels.ChangeEmailOtpVerifyApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityChangeOldEmailOtpBinding
import com.example.topaz.databinding.ActivityEmailChangeOtpBinding
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

private lateinit var binding: ActivityChangeOldEmailOtpBinding

class ChangeOldEmailOtp : AppCompatActivity() {
    lateinit var activity: Activity
    var custEmailId=""
    var custId=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeOldEmailOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this

        binding.resendOtp2.setOnClickListener {
            checkUserApiCall()
            countdownTimer()
        }

        val sharedPreference =  getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custEmailId = sharedPreference.getString("email","")!!
        custId = sharedPreference.getString("customercode","")!!



        binding.confirmEmailOtp.setOnClickListener {

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

                checkUserApiCall()
            }

        }
        countdownTimer()


        binding.otp01.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if(s.toString().length==1){
                    binding.otp02.requestFocus()
                }
                else{
                    binding.otp01.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // binding.otp02.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp02.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length==1){
                    binding.otp03.requestFocus()
                }
                else{
                    binding.otp02.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //binding.otp03.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp03.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length==1){
                    binding.otp04.requestFocus()
                }
                else{
                    binding.otp03.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //binding.otp04.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp04.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length==1){
                    binding.otp05.requestFocus()
                }
                else{
                    binding.otp04.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // binding.otp05.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp05.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length==1){
                    binding.otp06.requestFocus()
                }
                else{
                    binding.otp05.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //binding.otp06.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.otp06.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.confirmEmailOtp.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        //Handling delete functionality
        binding.otp02.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_DEL){
                    if(binding.otp02.text.toString().isEmpty()) {
                        binding.otp01.requestFocus()
                    }
                }

                return false
            }
        })
        binding.otp03.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_DEL){
                    if(binding.otp03.text.toString().isEmpty()) {
                        binding.otp02.requestFocus()
                    }
                }

                return false
            }
        })
        binding.otp04.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_DEL){
                    if(binding.otp04.text.toString().isEmpty()) {
                        binding.otp03.requestFocus()
                    }
                }

                return false
            }
        })
        binding.otp05.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_DEL){

                    if(binding.otp05.text.toString().isEmpty()) {
                        binding.otp04.requestFocus()
                    }

                }

                return false
            }
        })
        binding.otp06.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_DEL){

                    if(binding.otp06.text.toString().isEmpty()) {
                        binding.otp05.requestFocus()
                    }
                }

                return false
            }
        })

        binding.resendOtp2.setOnClickListener{
            //resendOTP()
            binding.appProgressBar.visibility = View.VISIBLE
            countdownTimer()
            changeOldEmailResendOTP()
        }



    }

    private fun checkUserApiCall() {
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        val recievedOtp = binding.otp01.text.toString() + binding.otp02.text.toString() +
                binding.otp03.text.toString() + binding.otp04.text.toString() +
                binding.otp05.text.toString() + binding.otp06.text.toString()


        val body: RequestBody = recievedOtp.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
        requestBodyMap["emailOtp"] = body

        res.verifyOldEmailOtp(requestBody = body)
            .enqueue(object : Callback<ChangeEmailOtpVerifyApiModel?> {
                override fun onResponse(
                    call: Call<ChangeEmailOtpVerifyApiModel?>,
                    response: Response<ChangeEmailOtpVerifyApiModel?>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Email Verified Sucessfully",
                            Toast.LENGTH_LONG
                        )
                            .show()


                        //val intent = Intent(this, ChangeEmail::class.java)
                        val intent = Intent(this@ChangeOldEmailOtp, ChangeEmail::class.java)
                        intent.putExtra("emailOtp", recievedOtp)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Something Went Wrong User Details Not updated",
                            Toast.LENGTH_LONG
                        )
                            .show()

                    }
                }

                override fun onFailure(call: Call<ChangeEmailOtpVerifyApiModel?>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Something Went Wrong User Details Not updated",
                        Toast.LENGTH_LONG
                    )
                        .show()

                }
            })

    }


    /* private fun createOTPRequestParams(emailot: String):JsonObject{
         var jsonObject=JsonObject()
         jsonObject.addProperty("",emailot)
         return Jsonoject().addProperty("","")
     }*/


    private fun resendOTP() {
        binding.appProgressBar.visibility = View.VISIBLE
        countdownTimer()
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

    fun changeOldEmailResendOTP(){
        try{

            val oldEmailRes = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                .create(JsonPlaceholder::class.java)

            oldEmailRes.verifyOldEmail(customerID =custId,emailObject(email = custEmailId) ).enqueue(object : Callback<ChangeEmailOtpApiModel?> {
                override fun onResponse(
                    call: Call<ChangeEmailOtpApiModel?>,
                    response: Response<ChangeEmailOtpApiModel?>
                ) {
                    binding.appProgressBar.visibility = View.GONE
                    if(response.isSuccessful){
                        Toast.makeText(this@ChangeOldEmailOtp,"OTP sent to registered email",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this@ChangeOldEmailOtp,"Something went wrong",Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<ChangeEmailOtpApiModel?>, t: Throwable) {
                    binding.appProgressBar.visibility = View.GONE
                    Toast.makeText(this@ChangeOldEmailOtp,"Something went wrong",Toast.LENGTH_LONG).show()
                }
            })




        }
        catch (e:Exception){
            binding.appProgressBar.visibility = View.GONE
            e.toString()
        }
    }


    private fun emailObject( email:String): JsonObject {
        val json = JsonObject()
        try {
            json.addProperty("email",  email)
            Log.d(ContentValues.TAG, "oncheckJson" + json.toString())
        }
        catch (e: Exception) {
        }

        return json
    }

}