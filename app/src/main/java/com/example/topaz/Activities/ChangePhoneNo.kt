package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import android.widget.ViewAnimator
import com.example.topaz.ApiModels.OldPhoneApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.Utility.Util
import com.example.topaz.databinding.ActivityChangeEmailBinding
import com.example.topaz.databinding.ActivityChangePhoneNoBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

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
            Util.hideKeyBoard(this,it)
            onApiCall(custId!!)
        }

        binding.backarrow1.setOnClickListener {
            finish()
        }

    }

    private fun onApiCall(custId : String) {

        try {

            binding.changeNewPhoneProgress?.visibility= View.VISIBLE

            var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                .create(JsonPlaceholder::class.java)

            val body: RequestBody = binding.changePhoneMobile.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
            requestBodyMap["phoneNumber"] = body


            res.verifyNewPhoneNumber(custId, body).enqueue(object : Callback<OldPhoneApiModel?> {
                override fun onResponse(
                    call: Call<OldPhoneApiModel?>,
                    response: Response<OldPhoneApiModel?>
                ) {
                    binding.changeNewPhoneProgress?.visibility= View.GONE
                    if (response.isSuccessful) {
                        Log.d(
                            ContentValues.TAG,
                            "onResponse new success phone: " + response.body().toString()
                        )
                        var message = "Otp Sent to the entered Mobile Number"
                        //message += "\n\n " + binding.phoneContainer.helperText
                        AlertDialog.Builder(this@ChangePhoneNo)
                            .setTitle("")
                            .setMessage(message)
                            .setPositiveButton("Ok") { _, _ ->
                                var intent = Intent(activity, ChangeNewPhoneOtp::class.java)
                                intent.putExtra(
                                    "extra_mobile",
                                    binding.changePhoneMobile.text.toString()
                                )
                                startActivity(intent)
                                finish()
                            }.show()

                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Something went wrong",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        Log.d(
                            ContentValues.TAG,
                            "onResponse new fail phone: " + response.body().toString()
                        )
                        binding.changeNewPhoneProgress?.visibility= View.GONE
                    }
                }

                override fun onFailure(call: Call<OldPhoneApiModel?>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Something went Wrong please try again Later",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    Log.d(ContentValues.TAG, "onResponse new Failure phone: " + t.message)
                    binding.changeNewPhoneProgress?.visibility= View.GONE
                }
            })
        }
        catch (e:Exception){
            e.toString()
            binding.changeNewPhoneProgress?.visibility= View.GONE
            Toast.makeText(
                applicationContext,
                "Something went Wrong please try again Later",
                Toast.LENGTH_LONG
            )
                .show()
        }

    }
}