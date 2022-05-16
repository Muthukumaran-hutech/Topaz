package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.topaz.ApiModels.ChangeEmailOtpApiModel
import com.example.topaz.ApiModels.UpdateUserApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityChangeEmailBinding
import com.example.topaz.databinding.ActivityChangeOldEmailBinding
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var binding: ActivityChangeOldEmailBinding


class ChangeOldEmail : AppCompatActivity() {
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeOldEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        val sharedPreference =  getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        var custId=sharedPreference.getString("customercode","")
        var custEmailId = sharedPreference.getString("email","")
        binding.changeEmail.setText(custEmailId)
        binding.changeEmail.isEnabled = false

        binding.categoryBackArrow.setOnClickListener{
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }

        binding.sendOtp.setOnClickListener{
            startActivity(Intent(activity, ChangeOldEmailOtp::class.java))

        }


        binding.sendOtp.setOnClickListener {
            checkUserApiCall(custId!!)
        }
    }

    private fun checkUserApiCall(custId:String) {
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        res.verifyOldEmail(custId,email(email = binding.changeEmail.text.toString())).enqueue(object :
            Callback<ChangeEmailOtpApiModel?>{
            override fun onResponse(
                call: Call<ChangeEmailOtpApiModel?>,
                response: Response<ChangeEmailOtpApiModel?>
            ) {
                if (response.isSuccessful){
                    Log.d("Update Res:", response.body()!!.email)
                    var message = "Otp Sent to the registered EmailId"
                    //message += "\n\n " + binding.phoneContainer.helperText
                    AlertDialog.Builder(this@ChangeOldEmail)
                        .setTitle("")
                        .setMessage(message)
                        .setPositiveButton("Ok") { _, _ ->
                            startActivity(Intent(activity, ChangeOldEmailOtp::class.java))
                            finish()
                        }.show()

                }else{
                    Log.d("Update Res on failure:", response.body()!!.email)
                }

            }

            override fun onFailure(call: Call<ChangeEmailOtpApiModel?>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Something Went Wrong User Details Not updated",
                    Toast.LENGTH_LONG
                )
                    .show()
                Log.d("Failure", t.message.toString())
            }

        })
    }

    private fun email( email:String): JsonObject {
        val json = JsonObject()
        try {
            json.addProperty("email",  email)
            Log.d(ContentValues.TAG, "oncheckJson" + json.toString())
        }
        catch (e: Exception) {
        }

        return json
    }



}