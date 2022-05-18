package com.example.topaz.Activities

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.topaz.ApiModels.OldPhoneApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityChangeEmailBinding
import com.example.topaz.databinding.ActivityChangePhoneNoBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePhoneNo : AppCompatActivity() {

    private lateinit var binding: ActivityChangePhoneNoBinding
    lateinit var activity: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePhoneNoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        activity = this
        val sharedPreference =  getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        var custId=sharedPreference.getString("customercode","")


        binding.backarrow1.setOnClickListener{
            startActivity(Intent(activity,EditProfile::class.java))
            finish()
        }
        binding.changeMobileNo.setOnClickListener {
            onApiCall(custId!!)
        }

    }

    private fun onApiCall(custId : String) {

        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        val body: RequestBody = binding.changePhoneMobile.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
        requestBodyMap["phoneNumber"] = body


        res.verifyNewPhoneNumber(custId, body).enqueue(object : Callback<OldPhoneApiModel?> {
            override fun onResponse(
                call: Call<OldPhoneApiModel?>,
                response: Response<OldPhoneApiModel?>
            ) {
                if (response.isSuccessful){
                    Log.d(ContentValues.TAG, "onResponse new success phone: "+ response.body().toString())
                    startActivity(Intent(activity, ChangeNewPhoneOtp::class.java))
                    finish()
                }else{
                    Log.d(ContentValues.TAG, "onResponse new fail phone: "+ response.body().toString())
                }
            }

            override fun onFailure(call: Call<OldPhoneApiModel?>, t: Throwable) {
                Log.d(ContentValues.TAG, "onResponse new Failure phone: "+ t.message)
            }
        })

    }
}