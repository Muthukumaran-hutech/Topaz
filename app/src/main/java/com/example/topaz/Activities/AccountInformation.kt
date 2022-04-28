package com.example.topaz.Activities

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.UpdateCustomerInfo
import com.example.topaz.Models.UpdateUserApiModel
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityAccountInformationBinding
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountInformation : AppCompatActivity() {

    private lateinit var binding: ActivityAccountInformationBinding
    lateinit var activity: Activity
    var ss = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            ss = intent.getStringExtra("phoneno1").toString()
        } catch (e: ExceptionInInitializerError) {
            Log.d("exception", e.toString())
        }
        activity = this

        binding.accountInfoPhNumber.setText(ss)
        //binding.accountInfoPhNumber.isEnabled = false
       // binding.accountInfoEmailId.isEnabled = false
       // binding.accountInfoCustomerId.isEnabled = false
        binding.accountInfoSkipBtn.setOnClickListener {
            startActivity(Intent(activity, HomeScreen::class.java))
            finish()
        }

        binding.accoutInfoBackkArrow.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
            finish()
        }


        binding.accountInfoUpdateBtn.setOnClickListener {

            updateuserinfo()


        }


        /*GlobalScope.launch {

            var updateres=res.updateInfo(customerDetails())
            if(updateres!=null){
                Log.d("Update res:",updateres.message)
            }

        }*/

    }

    private fun updateuserinfo() {

       var accountName = binding.accountInfoUserName.text
        var accountId = binding.accountInfoCustomerId.text
        var accountAddress = binding.accountInfoAddressLine.text
        var accountPhoneNo = ss
        var accountEmailId = binding.accountInfoEmailId.text
        var updateCustomerInfo=UpdateCustomerInfo(
            customerName = accountName.toString(),
            customerId = "CUS001",
            customerAddress = accountAddress.toString(),
            customerPhoneNo = accountPhoneNo,
            customerEmailAddress = accountEmailId.toString()


        )
       // customerDetails(updateCustomerInfo)


        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        res.updateInfo(customerDetails(updateCustomerInfo)).enqueue(object : Callback<UpdateUserApiModel?> {
            override fun onResponse(
                call: Call<UpdateUserApiModel?>,
                response: Response<UpdateUserApiModel?>
            ) {

                if (response.isSuccessful) {

                    Toast.makeText(
                        applicationContext,
                        "Details Updated Sucessfully",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    startActivity(Intent(activity, HomeScreen::class.java))
                    finish()
                    Log.d("Update Res:", response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<UpdateUserApiModel?>, t: Throwable) {
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

    private fun customerDetails(updateCustomerInfo: UpdateCustomerInfo): JsonObject {
        val json = JsonObject()
        try {

            json.addProperty("customerID",  updateCustomerInfo.customerId)
            json.addProperty("customerName", updateCustomerInfo.customerName)
            json.addProperty("primaryPhonenumber",  updateCustomerInfo.customerPhoneNo)
            json.addProperty("email",  updateCustomerInfo.customerEmailAddress)
            json.addProperty("addressLine",  updateCustomerInfo.customerAddress)


            /*val accountdetails = JsonObject()
            accountdetails.addProperty("accountid",1)
            accountdetails.addProperty("accountNumber","HDFC33093")
            accountdetails.addProperty("ifscCode","HDFC1111")
            accountdetails.addProperty("branchName","hdfc")
            accountdetails.addProperty("upiNumber","ABCD111")

            json.addProperty("accountdetails",accountdetails.toString())

         */   Log.d(ContentValues.TAG, "oncheckJson" + json.toString())
        } catch (e: Exception) {
        }

        return json
    }
}