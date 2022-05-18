package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.topaz.ApiModels.VerifyNewEmailApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityCategoryBinding
import com.example.topaz.databinding.ActivityChangeEmailBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeEmail : AppCompatActivity() {

    private lateinit var binding: ActivityChangeEmailBinding
    lateinit var activity: Activity
    var mail = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mail = intent.getStringExtra("emailOtp").toString()
        activity = this


        binding.categoryBackArrow.setOnClickListener {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }




        binding.sendOtp.setOnClickListener {
            onApiCall()
        }
    }

    private fun onApiCall() {
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        val body: RequestBody =
            binding.changeEmail.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
        requestBodyMap["email"] = body

        res.verifyNewEmail(mail, body).enqueue(object : Callback<VerifyNewEmailApiModel?> {
            override fun onResponse(
                call: Call<VerifyNewEmailApiModel?>,
                response: Response<VerifyNewEmailApiModel?>
            ) {
                if (response.isSuccessful) {
                    var message = "Otp Sent to the entered EmailId"
                    //message += "\n\n " + binding.phoneContainer.helperText
                    AlertDialog.Builder(this@ChangeEmail)
                        .setTitle("")
                        .setMessage(message)
                        .setPositiveButton("Ok") { _, _ ->
                            startActivity(Intent(activity, EmailChangeOtp::class.java))
                            finish()
                        }.show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Something Went Wrong",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }

            override fun onFailure(call: Call<VerifyNewEmailApiModel?>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Something Went Wrong",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        })
    }
}