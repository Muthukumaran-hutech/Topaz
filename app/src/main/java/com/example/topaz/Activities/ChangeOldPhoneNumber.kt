package com.example.topaz.Activities

import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.topaz.ApiModels.OldPhoneApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityChangeOldPhoneNumberBinding
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeOldPhoneNumber : AppCompatActivity() {

    private lateinit var binding: ActivityChangeOldPhoneNumberBinding
    lateinit var activity: Activity
    var phone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeOldPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this

        val sharedPreference =  getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        var custId=sharedPreference.getString("customercode","")
        var custPhoneno = sharedPreference.getString("primaryPhonenumber","")


        binding.changePhone.setText(custPhoneno)
        binding.changePhone.isEnabled = false

        binding.categoryBackArrow.setOnClickListener{
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }




        binding.sendOtp.setOnClickListener {
            checkUserApiCall(custId!!)


        }
    }

    private fun checkUserApiCall(custId : String) {
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        val body: RequestBody = binding.changePhone.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
        requestBodyMap["phoneNumber"] = body

        res.verifyOldPhoneNumber(custId,body).enqueue(object : Callback<OldPhoneApiModel?> {
            override fun onResponse(
                call: Call<OldPhoneApiModel?>,
                response: Response<OldPhoneApiModel?>
            ) {
                if (response.isSuccessful){
                    Log.d(TAG, "onResponse succes phone: "+ response.body().toString())
                    startActivity(Intent(activity, ChangeOldphoneOtp::class.java))
                    finish()
                }else{
                    Log.d(TAG, "onResponse Fail phone: "+ response.body().toString())
                }
            }

            override fun onFailure(call: Call<OldPhoneApiModel?>, t: Throwable) {
                Log.d(TAG, "onResponse Failure phone: "+ t.message)
            }
        })

    }

}
