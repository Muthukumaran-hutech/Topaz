package com.example.topaz.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.topaz.databinding.ActivityOtpVerfificationBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class OtpVerfification : AppCompatActivity() {


    private lateinit var binding: ActivityOtpVerfificationBinding


    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private lateinit var mcallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var mVerificationId: String? = null
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var activity: Activity
    var ss = ""
    var cs = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerfificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            cs = intent.getStringExtra("countrycode").toString()
            ss = intent.getStringExtra("phoneno").toString()
        } catch (e: ExceptionInInitializerError) {
            Log.d("exception", e.toString())
        }

        firebaseAuth = Firebase.auth
        activity = this

        binding.loginBtnPhone.setOnClickListener {
            startActivity(Intent(activity, AccountInformation::class.java))
            finish()
        }
        binding.loginBackarrow1.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
            finish()
        }
        
        binding.loggedinNumberText.setText(cs+ss)


        mcallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(ContentValues.TAG, "onVerificationCompleted:$credential")
                //pHONEAUTH
                val smscode = credential.smsCode
                //
                signInWithPhoneAuthCredential(credential, smscode!!)
            }


            private fun signInWithPhoneAuthCredential(
                credential: PhoneAuthCredential,
                smscode: String
            ) {
                val credential = PhoneAuthProvider.getCredential(mVerificationId!!, smscode)
                firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this@OtpVerfification, OnCompleteListener {

                        if (it.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success")

                            val user = it.result?.user
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", it.exception)
                            if (it.exception is FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                            // Update UI
                        }

                    })
                /*.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")

                        val user = task.result?.user
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                        // Update UI
                    }
                }*/
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(ContentValues.TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(ContentValues.TAG, "onCodeSent:$verificationId")
                // val credential = PhoneAuthProvider.getCredential(verificationId!!, )
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId
                forceResendingToken = token
            }
        }
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(cs + ss)       // Phone number to verify
            .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(mcallBacks!!) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        countdownTimer()

        binding.resendOtpBtn.setOnClickListener {
            countdownTimer()
            //Resend function
            resendOTP()

        }


    }

    private fun resendOTP() {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(cs + ss)       // Phone number to verify
            .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(mcallBacks!!)
            .setForceResendingToken(forceResendingToken!!)// OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun countdownTimer() {
        object : CountDownTimer(120000, 1000) {

            // Callback function, fired on regular interval
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                binding.countdownTimer.text = (minutes.toString() + ":" + seconds.toString())
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                binding.countdownTimer.text = binding.otpNtRecieved.toString()
                binding.otpNtRecieved.visibility = View.VISIBLE
                binding.resendOtpBtn.visibility = View.VISIBLE
                binding.countdownTimer.visibility = View.GONE
            }
        }.start()
    }




    // time count down for 30 seconds,
    // with 1 second as countDown interval


}




