package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import com.example.topaz.R
import com.example.topaz.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.hbb20.CountryCodePicker
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    //dataBinding
    private lateinit var binding: ActivityLoginBinding
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private var mcallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mVerificationId: String? = null
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var activity: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.phoneContinueBtn.setOnClickListener {
            //  submitForm()
            phoneFocusListner()
            var intent = Intent(this, OtpVerfification::class.java)
            intent.putExtra("countrycode", "+91")
            intent.putExtra("phoneno", binding.phoneNoEditText.text.toString())
            startActivity(intent)
            /* startActivity(Intent(activity, OtpVerfification::class.java))
             finish()*/
        }


    }


    private fun submitForm() {
        val validPhone = binding.phoneContainer.helperText == null
        if (validPhone) {
            startActivity(Intent(activity, OtpVerfification::class.java))
            finish()
        } else {
            inValidForm()
        }
    }

    private fun resetForm() {
        var message = ""
        if (binding.phoneContainer.helperText == null)
            message += "\n\nPhone: " + binding.phoneContainer.helperText

        AlertDialog.Builder(this)
            .setTitle("Form Submitted")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                binding.phoneNoEditText.text = null

                //  binding.phoneContainer.helperText = getString(R.id.Required)
            }.show()
    }

    private fun inValidForm() {
        var message = "Please Enter a Valid Number"
        if (binding.phoneContainer.helperText == null)
            message += "\n\nPhone: " + binding.phoneContainer.helperText
        AlertDialog.Builder(this)
            .setTitle("Invalid Number")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                // do Nothing
            }.show()
    }

    private fun phoneFocusListner() {
        binding.phoneNoEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.phoneContainer.helperText = validPhone()
            }
        }
    }


    private fun validPhone(): String? {
        val phoneText = binding.phoneNoEditText.text.toString()
        if (phoneText.matches(".*[0-9].*".toRegex())) {
            return "Must Be all Digits"
        }
        if (phoneText.length != 10) {
            return "Must Be 10 Digits"
        }

        return null
    }



}
