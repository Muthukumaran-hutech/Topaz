package com.example.topaz.Activities

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.UpdateCustomerInfo
import com.example.topaz.ApiModels.UpdateUserApiModel
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityAccountInformationBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountInformation : AppCompatActivity() {

    private lateinit var binding: ActivityAccountInformationBinding
    lateinit var activity: Activity
    var ss = ""
    private lateinit var database:DatabaseReference




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

        val sharedPreference =  getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        val custName = sharedPreference.getString("customerName","")
        val custId = sharedPreference.getString("customercode","")
        val custAddress = sharedPreference.getString("addressLine","")
        val custPhoneno = sharedPreference.getString("primaryPhonenumber","")
        val custEmailId = sharedPreference.getString("email","")
        //set the edittext

        var updateCustomerInfo1 = UpdateCustomerInfo(
            customerName = custName.toString(),
            customerId = custId.toString(),
            customerPhoneNo = custPhoneno!!,
            customerEmailAddress = custEmailId.toString()
        )

        //creating database and child to fire base
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(custId.toString()).setValue(updateCustomerInfo1)



        binding.accountInfoUserName.setText(custName.toString())
        binding.accountInfoCustomerId.setText(custId.toString())
        binding.accountInfoAddressLine.setText(custAddress.toString())
        binding.accountInfoPhNumber.setText(custPhoneno.toString())
        binding.accountInfoEmailId.setText(custEmailId.toString())

        binding.accountInfoPhNumber.setText(ss)
        binding.accountInfoPhNumber.isEnabled = false
        binding.accountInfoEmailId.isEnabled = false
        binding.accountInfoCustomerId.isEnabled = false
        binding.accountInfoSkipBtn.setOnClickListener {
            startActivity(Intent(activity, HomeScreen::class.java))
            finish()
        }

        binding.accoutInfoBackkArrow.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
            finish()
        }


        binding.accountInfoUpdateBtn.setOnClickListener {
           if (validateUserInfo()){
               updateuserinfo()
           }

        }


        /*GlobalScope.launch {

            var updateres=res.updateInfo(customerDetails())
            if(updateres!=null){
                Log.d("Update res:",updateres.message)
            }

        }*/

    }

    private fun validateUserInfo(): Boolean {
        val name: String = binding.accountInfoUserName.getText().toString()
        val custId : String = binding.accountInfoCustomerId.getText().toString()
        val address : String = binding.accountInfoAddressLine.getText().toString()
        val phone : String = binding.accountInfoPhNumber.getText().toString()
        val email : String = binding.accountInfoEmailId.getText().toString()
        val emailPattern  = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (name.isEmpty()) {
            binding.accountInfoUserName.error = "Field cannot be empty"
            return false
        } else if (name.length >= 50) {
            binding.accountInfoUserName.error = "Username too long"
            return false;
        }
        else if(custId.isEmpty()) {
            binding.accountInfoCustomerId.error = "Field cannot be empty"
            return false
        } else if (custId.length <= 4) {
            binding.accountInfoCustomerId.error = "ustomer Id too short"
            return false;
        }
        else if(address.isEmpty()) {
            binding.accountInfoAddressLine.error = "Field cannot be empty"
            return false
        } else if (address.length <= 5) {
            binding.accountInfoAddressLine.error = "Address too Short"
            return false;
        }
        else if(phone.isEmpty()) {
            binding.accountInfoPhNumber.error = "Field cannot be empty"
            return false
        } else if (phone.length < 10) {
            binding.accountInfoPhNumber.error = "Please enter a valid phone no"
            return false;
        }
        else if(email.isEmpty()) {
            binding.accountInfoEmailId.error = "Field cannot be empty"
            return false
        } else if (!email.matches(Regex(emailPattern))) {
            binding.accountInfoEmailId.error = "Please enter a valid email id"
            return false;
        }
        ///
        ///
        ///
        else {
            binding.accountInfoUserName.error = null
            binding.accountInfoCustomerId.error = null
            binding.accountInfoAddressLine.error = null
            binding.accountInfoPhNumber.error = null
            binding.accountInfoEmailId.error = null
            binding.accountInfoUserName.isEnabled = false
            binding.accountInfoCustomerId.isEnabled = false
            binding.accountInfoAddressLine.isEnabled = false
            binding.accountInfoPhNumber.isEnabled = false
            binding.accountInfoEmailId.isEnabled = false
           return true
        }








    }

    private fun updateuserinfo() {

       var accountName = binding.accountInfoUserName.text
        var accountId = binding.accountInfoCustomerId.text
        var accountAddress = binding.accountInfoAddressLine.text
        var accountPhoneNo = ss
        var accountEmailId = binding.accountInfoEmailId.text
        var updateCustomerInfo=UpdateCustomerInfo(
            customerName = accountName.toString(),
            customerId = accountId.toString(),
            customerAddress = accountAddress.toString(),
            customerPhoneNo = accountPhoneNo,
            customerEmailAddress = accountEmailId.toString()


        )
       // customerDetails(updateCustomerInfo)


        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)
        val sharedPreference =  getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        var custId=sharedPreference.getString("customercode","")
        res.updateInfo(custId!!,customerDetails(updateCustomerInfo)).enqueue(object : Callback<UpdateUserApiModel?> {
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