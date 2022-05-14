package com.example.topaz.Activities

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.example.topaz.R
import com.example.topaz.databinding.ActivityChangeOldEmailBinding
import com.example.topaz.databinding.ActivityEmailChangeOtpBinding
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

private lateinit var binding: ActivityEmailChangeOtpBinding
class EmailChangeOtp : AppCompatActivity() {
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailChangeOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        countdownTimer()
    }



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
}