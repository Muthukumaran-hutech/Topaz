package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.R
import androidx.core.text.HtmlCompat
import com.example.topaz.ApiModels.CheckUserApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.Utility.Util
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



         setSubtitleText()
        binding.phoneContinueBtn.setOnClickListener {
            //Util.hideKeyBoard(this,it)
            if(isEnabled&&validatePhoneNumber()) {
                binding.loginProgress?.visibility=View.VISIBLE
                checkUserApiCall()
                isEnabled = false
            }


        }
        binding.phoneNoEditText.isFocusableInTouchMode=true


    }

    private fun setSubtitleText() {
      val firsttext=getString(com.example.topaz.R.string.dig)
        val secondtext= "<font color='#0000'>"+getString(com.example.topaz.R.string.six)+"</font>"
        val secontextbold="<b>"+secondtext+"</b>"
        val thirdtext=getString(com.example.topaz.R.string.vernum)
        binding.textView2.setText(HtmlCompat.fromHtml(firsttext+" "+secontextbold+" "+thirdtext,HtmlCompat.FROM_HTML_MODE_LEGACY))
    }

    private fun checkUserApiCall() {
        val res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
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
                   binding.loginProgress?.visibility=View.GONE
                   response.body()?.let { saveServerData(it) }

                   submitForm()

               }
               else{
                   binding.loginProgress?.visibility=View.GONE
                   val message = "This is not a Registered number"
                   AlertDialog.Builder(this@LoginActivity)
                       .setTitle("Please contact your Administrator ")
                       .setMessage(message)
                       .setPositiveButton("OK") { _, _ ->

                       }.show()

                   isEnabled=true
               }

           }

           override fun onFailure(call: Call<CheckUserApiModel?>, t: Throwable) {
               binding.loginProgress?.visibility=View.GONE
               Toast. makeText(applicationContext,"Something Went Wrong Please Try Again Later", Toast.LENGTH_LONG).show()
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
        //editor.putString("addressLine",checkUserApiModel.city)
        editor.putString("state", checkUserApiModel.state.stateName.toString())
        // editor.putString("secondaryPhonenumber","")
        editor.putString("city",checkUserApiModel.city)
        editor.putString("zipcode",checkUserApiModel.zipcode.toString())
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

    private fun validatePhoneNumber():Boolean{
        var isvalid=true
        if(binding.phoneNoEditText.text.toString().isEmpty()){
            isvalid=false
            Toast.makeText(this,"Phone number cannot be empty",Toast.LENGTH_LONG).show()
        }
        else if(binding.phoneNoEditText.text.length<10){
            isvalid=false
            Toast.makeText(this,"Enter a valid phone number",Toast.LENGTH_LONG).show()
        }


        return isvalid
    }



}
