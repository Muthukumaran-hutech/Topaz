package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.topaz.ApiModels.CheckUserApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityLoginBinding
import com.google.firebase.auth.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    //dataBinding
    private lateinit var binding: ActivityLoginBinding
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private var mcallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mVerificationId: String? = null
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var activity: Activity
    var isEnabled=true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.phoneContinueBtn.setOnClickListener {


            if(isEnabled) {
                checkUserApiCall()
                isEnabled = false
            }


        }


    }

    private fun checkUserApiCall() {
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        val body: RequestBody = binding.phoneNoEditText.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
        requestBodyMap["primaryPhonenumber"] = body


       res.checkIfCustomerExists(requestBody = body).enqueue(object : Callback<CheckUserApiModel?> {
           override fun onResponse(
               call: Call<CheckUserApiModel?>,
               response: Response<CheckUserApiModel?>
           ) {

               if(response.isSuccessful){
                   response.body()?.let { saveServerData(it) }

                   submitForm()

               }
               else{
                   val message = "This is not a Registerd number"
                   AlertDialog.Builder(this@LoginActivity)
                       .setTitle("Please contact your Administrator ")
                       .setMessage(message)
                       .setPositiveButton("OK") { _, _ ->

                       }.show()

                   isEnabled=true
               }

           }

           override fun onFailure(call: Call<CheckUserApiModel?>, t: Throwable) {
               Toast. makeText(applicationContext,"Something Went Wronng Please Try Again Later", Toast.LENGTH_LONG).show()
               isEnabled=true
           }
       })
    }

    private fun saveServerData(checkUserApiModel: CheckUserApiModel) {
        val sharedPreference =  getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("customercode",checkUserApiModel.customercode)
        editor.putString("customerName",checkUserApiModel.customerName)
        editor.putString("primaryPhonenumber",checkUserApiModel.primaryPhonenumber)
        editor.putString("email",checkUserApiModel.email)
        editor.putString("addressLine",checkUserApiModel.addressLine)
        editor.putString("addressLine",checkUserApiModel.city)
        editor.putString("isUserLoggedIn","true")
        // editor.putString("secondaryPhonenumber","")
        //editor.putString("city",checkUserApiModel.city)
        //editor.putLong("l",100L)
        editor.apply()
    }


    private fun submitForm() {
        val validPhone = binding.phoneNoEditText.text
        if (validPhone.toString().length==10) {
            var intent = Intent(this, OtpVerfification::class.java)
            intent.putExtra("countrycode", "+91")
            intent.putExtra("phoneno", binding.phoneNoEditText.text.toString())
            startActivity(intent)
        } else {
            inValidForm()
        }
    }



    private fun inValidForm() {
        var message = "Please Enter a Valid Number"
        //message += "\n\n " + binding.phoneContainer.helperText
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
             //   binding.phoneContainer.helperText = validPhone()
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
