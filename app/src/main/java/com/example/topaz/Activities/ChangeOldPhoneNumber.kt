package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.topaz.ApiModels.OldPhoneApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.Utility.Util
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
        val custId=sharedPreference.getString("customercode","")
        val custPhoneno = sharedPreference.getString("primaryPhonenumber","")


        binding.changePhone.setText(custPhoneno)
        binding.changePhone.isEnabled = false

        binding.categoryBackArrow.setOnClickListener{
            finish()
        }




        binding.sendOtp.setOnClickListener {
            Util.hideKeyBoard(this,it)
            checkUserApiCall(custId!!)


        }
    }

    private fun checkUserApiCall(custId : String) {
        try {

            binding.changeOldPhoneProgress.visibility= View.VISIBLE
            val res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                .create(JsonPlaceholder::class.java)

            val body: RequestBody =
                binding.changePhone.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
            requestBodyMap["phoneNumber"] = body

            res.verifyOldPhoneNumber(custId, body).enqueue(object : Callback<OldPhoneApiModel?> {
                override fun onResponse(
                    call: Call<OldPhoneApiModel?>,
                    response: Response<OldPhoneApiModel?>
                ) {
                    binding.changeOldPhoneProgress.visibility= View.GONE
                    if (response.isSuccessful) {
                        Log.d(TAG, "onResponse succes phone: " + response.body().toString())
                        val message = "Otp Sent to the entered Registered Mobile Number"
                        AlertDialog.Builder(this@ChangeOldPhoneNumber)
                            .setTitle("")
                            .setMessage(message)
                            .setPositiveButton("Ok") { _, _ ->
                                val intent = Intent(activity, ChangeOldphoneOtp::class.java)
                                intent.putExtra("extra_mobile", binding.changePhone.text.toString())
                                startActivity(intent)
                                finish()
                            }.show()


                    } else {
                        Log.d(TAG, "onResponse Fail phone: " + response.body().toString())
                        binding.changeOldPhoneProgress.visibility= View.GONE
                        Toast.makeText(this@ChangeOldPhoneNumber,"Something went wrong",Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<OldPhoneApiModel?>, t: Throwable) {
                    Log.d(TAG, "onResponse Failure phone: " + t.message)
                    binding.changeOldPhoneProgress.visibility= View.GONE
                    Toast.makeText(this@ChangeOldPhoneNumber,"Something went wrong",Toast.LENGTH_LONG).show()
                }
            })

        }
        catch (e:Exception){
            e.toString()
            binding.changeOldPhoneProgress.visibility= View.GONE
            Toast.makeText(this@ChangeOldPhoneNumber,"Something went wrong",Toast.LENGTH_LONG).show()
        }

    }


}
