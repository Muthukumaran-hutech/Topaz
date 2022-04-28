package com.example.topaz.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.topaz.databinding.ActivityOtpVerfificationBinding
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import kotlin.math.log

class OtpVerfification : AppCompatActivity() {


    private lateinit var binding: ActivityOtpVerfificationBinding


    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private lateinit var mcallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var mVerificationId: String? = null
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var activity: Activity
    var ss = ""
    var cs = ""
    private val REQ_USER_CONSENT = 200




    @RequiresApi(Build.VERSION_CODES.O)
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

        binding.appProgressBar.visibility = View.VISIBLE
        binding.loginBtnPhone.isEnabled = false
        binding.loginBtnPhone.setOnClickListener {

            if (binding.OTP1.text.toString().trim().isEmpty()
                || binding.OTP2.text.toString().trim().isEmpty()
                || binding.OTP3.text.toString().trim().isEmpty()
                || binding.OTP4.text.toString().trim().isEmpty()
                || binding.OTP5.text.toString().trim().isEmpty()
                || binding.OTP6.text.toString().trim().isEmpty()
            ) {

                Toast.makeText(applicationContext, "Please Enter The Valid OTP", Toast.LENGTH_LONG)
                    .show()
            } else {
                binding.appProgressBar.visibility = View.VISIBLE
                var otpCode =
                    (binding.OTP1.text.toString() + binding.OTP2.text.toString() + binding.OTP3.text.toString()
                            + binding.OTP4.text.toString() + binding.OTP5.text.toString() + binding.OTP6.text.toString())
                signInWithPhoneAuthCredential(otpCode)
            }

            /* startActivity(Intent(activity, AccountInformation::class.java))
             finish()*/
        }
        binding.loginBackarrow1.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
            finish()
        }

        binding.loggedinNumberText.setText(cs + ss)


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
                binding.appProgressBar.visibility = View.GONE
                binding.loginBtnPhone.isEnabled = true

                val smscode = credential.smsCode
                Toast.makeText(applicationContext, smscode, Toast.LENGTH_LONG).show()
                //getOtpFromMessage(smscode)

                //

            }


            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(ContentValues.TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    //binding.appProgressBar.visibility = View.VISIBLE
                    Toast.makeText(applicationContext, "Please Enter The Valid OTP", Toast.LENGTH_LONG)
                        .show()
                } else if (e is FirebaseTooManyRequestsException) {
                    binding.appProgressBar.visibility = View.VISIBLE
                    binding.loginBtnPhone.isEnabled = false
                    Toast.makeText(applicationContext, "Something went wrong Please try again Later", Toast.LENGTH_LONG)
                        .show()
                }

                // Show a message and update the UI
            }

            @RequiresApi(Build.VERSION_CODES.O)
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
                binding.appProgressBar.visibility = View.GONE
                binding.loginBtnPhone.isEnabled = true
                //progressBar()

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

            //Resend function
            resendOTP()

        }
        binding.OTP1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.OTP2.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.OTP2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.OTP3.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.OTP3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.OTP4.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.OTP4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.OTP5.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.OTP5.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.OTP6.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.OTP6.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.loginBtnPhone.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


    }

    private fun progressBar() {




    }

    private fun signInWithPhoneAuthCredential(
        //  credential: PhoneAuthCredential,
        smscode: String
    ) {

        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, smscode)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this@OtpVerfification, OnCompleteListener {

                if (it.isSuccessful) {
                    var intent = Intent(this, AccountInformation::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.putExtra("phoneno1", ss.toString())
                    startActivity(intent)
                    finish()

                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    //val user = it.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", it.exception)
                    if (it.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(applicationContext, "Please Enter The Valid OTP", Toast.LENGTH_LONG)
                            .show()
                    }
                    // Update UI
                }

            })

    }


    private fun resendOTP() {
        binding.appProgressBar.visibility = View.VISIBLE
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(cs + ss)       // Phone number to verify
            .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(mcallBacks!!)
            //  .setForceResendingToken(forceResendingToken!!)// OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
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
                    binding.countdownTimer.text =
                        ("0" + minutes.toString() + ":" + "0" + seconds.toString())
                } else {
                    binding.countdownTimer.text =
                        ("0" + minutes.toString() + ":" + seconds.toString())
                }
                binding.countdownTimer.visibility = View.VISIBLE
                binding.otpNtRecieved.visibility = View.GONE
                binding.resendOtpBtn.visibility = View.GONE

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
                binding.countdownTimer.text = binding.otpNtRecieved.toString()
                binding.otpNtRecieved.visibility = View.VISIBLE
                binding.resendOtpBtn.visibility = View.VISIBLE
                binding.appProgressBar.visibility = View.GONE
                binding.countdownTimer.visibility = View.GONE
            }
        }.start()


    }


}




