package com.example.topaz.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.topaz.ApiModels.OldPhoneApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.Utility.Util
import com.example.topaz.databinding.ActivityChangeNewPhoneOtpBinding
import com.example.topaz.databinding.ActivityChangeOldphoneOtpBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeNewPhoneOtp : AppCompatActivity() {
    private lateinit var binding: ActivityChangeNewPhoneOtpBinding
    lateinit var activity: Activity
    var phoneChange = ""
    lateinit var sharedPreference : SharedPreferences
    var custId = ""
    var mobNumber = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeNewPhoneOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this

        val item = intent.getStringExtra("extra_mobile")
        if (item != null) {
            phoneChange =item
        }

        binding.resendOtp2.setOnClickListener {
           // checkUserApiCall(custId)
            binding.newPhoneChangeProgress.visibility= View.VISIBLE
            resendNewPhoneOTP()
            countdownTimer()

        }


        binding.categoryBackArrow.setOnClickListener {
            finish()
        }
        sharedPreference =  getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId= sharedPreference.getString("customercode","").toString()

        binding.confirmPhoneOtp2.setOnClickListener {

            Util.hideKeyBoard(this,it)
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
                checkUserApiCall(custId)

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
                binding.confirmPhoneOtp2.requestFocus()
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

    private fun checkUserApiCall(custId: String) {
        val res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)



        val recievedOtp2= binding.otp01.text.toString() + binding.otp02.text.toString() +
                binding.otp03.text.toString() + binding.otp04.text.toString() +
                binding.otp05.text.toString() + binding.otp06.text.toString()

        val body: RequestBody = recievedOtp2.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
        requestBodyMap["phoneotp"] = body

        res.verifyNewPhoneOtp(custId,body).enqueue(object : Callback<ChangeNewEmailOtpApiModel?> {
            override fun onResponse(
                call: Call<ChangeNewEmailOtpApiModel?>,
                response: Response<ChangeNewEmailOtpApiModel?>
            ) {
                binding.appProgressBar.visibility = View.GONE
                if (response.isSuccessful){
                    Log.d(ContentValues.TAG, "onResponse otp succes phone: "+ response.body().toString())
                    val editor = sharedPreference.edit()
                    editor.putString("primaryPhonenumber",phoneChange).apply()
                    Log.d(ContentValues.TAG, "onResponse: Success " + response.body().toString())
                    binding.appProgressBar.visibility = View.GONE
                    startActivity(Intent(activity, EditProfile::class.java))
                    finish()
                }else{
                    Toast.makeText(this@ChangeNewPhoneOtp,"Invalid OTP",Toast.LENGTH_LONG).show()
                    //Log.d(ContentValues.TAG, "onResponse otp fail phone: "+ response.body().toString())
                }
            }

            override fun onFailure(call: Call<ChangeNewEmailOtpApiModel?>, t: Throwable) {
                Log.d(ContentValues.TAG, "onResponse otp failure phone: "+ t.message)
                binding.appProgressBar.visibility = View.GONE
            }
        })
    }


    private fun resendNewPhoneOTP(){
        try{
            val resendRes = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                .create(JsonPlaceholder::class.java)

            val body: RequestBody = mobNumber.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
            requestBodyMap["phoneNumber"] = body

            resendRes.verifyNewPhoneNumber(customerID = custId,body).enqueue(object : Callback<OldPhoneApiModel?> {
                override fun onResponse(
                    call: Call<OldPhoneApiModel?>,
                    response: Response<OldPhoneApiModel?>
                ) {
                    binding.newPhoneChangeProgress.visibility= View.GONE

                    if(response.isSuccessful){
                        Toast.makeText(this@ChangeNewPhoneOtp,"OTP sent to the entered number", Toast.LENGTH_LONG).show()
                    }else{

                        Toast.makeText(this@ChangeNewPhoneOtp,"Something went wrong", Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<OldPhoneApiModel?>, t: Throwable) {
                    binding.newPhoneChangeProgress.visibility= View.GONE
                 Toast.makeText(this@ChangeNewPhoneOtp,"Something went wrong", Toast.LENGTH_LONG).show()
                }
            })



        }
        catch (e:Exception){
            binding.newPhoneChangeProgress.visibility= View.GONE
            e.toString()
        }

    }
}