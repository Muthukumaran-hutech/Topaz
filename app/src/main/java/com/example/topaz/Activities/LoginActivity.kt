package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.topaz.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.hbb20.CountryCodePicker

class LoginActivity : AppCompatActivity() {

    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null

    private var mcallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mVerificationId: String? = null
    private lateinit var firebaseAuth: FirebaseAuth

    private var continueBtn: Button? = null
    private var countryCode: CountryCodePicker? = null
    private var phoneNo: EditText? = null
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        continueBtn = findViewById(R.id.continue_btn)
        countryCode = findViewById(R.id.countryCodePicker)
        phoneNo = findViewById(R.id.phone_no)
        activity = this

        continueBtn?.setOnClickListener{
            startActivity(Intent(activity,OtpVerfification::class.java))
            finish()
        }

/*
        // show password rules in text view
        textView.text = "Password minimum length 8"
        textView.append("\n1 uppercase")
        textView.append("\n1 number")
        textView.append("\n1 special character")


        // add text changed listener for edit text
        editText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?,
                                           start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?,
                                       start: Int, before: Int, count: Int) {
                s?.apply {
                    // check user input a valid formatted password
                    if (isValidPassword() && toString().length>=8) {
                        editText.error == null
                    }else{
                        // show error on input invalid password
                        editText.error = "invalid password."
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })*/

    }
}