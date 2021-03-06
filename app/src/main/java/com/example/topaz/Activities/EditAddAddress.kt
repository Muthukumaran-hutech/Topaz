package com.example.topaz.Activities

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.topaz.ApiModels.UpdateUserApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.SaveAddressModel
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityEditAddAddressBinding
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditAddAddress : AppCompatActivity() {

    private lateinit var binding: ActivityEditAddAddressBinding

    lateinit var activity: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)


        activity = this
        setSupportActionBar(binding.editAddressToolbar)
        supportActionBar?.title = ""

        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        var custName = sharedPreference.getString("customerName", "")
        var custId = sharedPreference.getString("customercode", "")
        var custAddress = sharedPreference.getString("addressLine", "")
        var custPhoneno = sharedPreference.getString("primaryPhonenumber", "")
        var custEmailId = sharedPreference.getString("email", "")
        var custCity = sharedPreference.getString("city", "")
        var custState = sharedPreference.getString("state", "")

        binding.nameAddress.setText(custName)
        binding.phoneNoChange.setText(custPhoneno)
        binding.locality.setText(custAddress)
        binding.houseNo.setText(custAddress)
        binding.city.setText(custCity)
        binding.state.setText(custState)


        binding.categoryBackArrow.setOnClickListener {
           /* intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()*/
        }

        binding.saveAddressBtn.setOnClickListener {
            updateuserinfo()
        }



    }

    private fun updateuserinfo() {
        var accountName = binding.nameAddress.text
        var accountPhoneNo = binding.phoneNoChange.text.toString()
        var locality = binding.locality.text
        var houseNo = binding.houseNo.text
        var pincode = binding.pincode.text.toString()
        var city = binding.city.text
        var state = binding.state.text
        var landmark = binding.landmark.text
        var address = locality.toString() + houseNo.toString() + landmark.toString()

        var saveAddressModel = SaveAddressModel(
            customerName = accountName.toString(),
            primaryPhonenumber = accountPhoneNo.toLong(),
            addressLine = address.toString(),
            zipcode = pincode.toLong(),
            city = city.toString(),
            stateName = state.toString()

        )

        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)
        val sharedPreference =  getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        var custId=sharedPreference.getString("customercode","")
        res.saveAddress(custId!!,customerDetails(saveAddressModel)).enqueue(object : Callback<UpdateUserApiModel?> {
            override fun onResponse(
                call: Call<UpdateUserApiModel?>,
                response: Response<UpdateUserApiModel?>
            ) {
                if (response.isSuccessful){
                    Log.d("Update Res success:", response.body()!!.message)
                    Toast.makeText(
                        applicationContext,
                        "Details Updated Sucessfully",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    startActivity(Intent(activity, MyAccount::class.java))
                    finish()
                }
                else{
                 //  Log.d("Update Res fail:", response.body()!!.message)
                }

            }

            override fun onFailure(call: Call<UpdateUserApiModel?>, t: Throwable) {
                t.message?.let { Log.d("Update Res failure:", it) }
            }
        })

    }
}



private  fun customerDetails(saveAddressModel: SaveAddressModel): JsonObject {
    var json2 = JsonObject()
   // json2.addproperty("stateName","Karnataka")
    json2.addProperty("stateName",saveAddressModel.stateName)
    var countryjson= JsonObject()
    countryjson.addProperty("countryName","")
    var accountdetails=JsonObject()
    accountdetails.addProperty("accountNumber","")
    accountdetails.addProperty("ifscCode","")
    accountdetails.addProperty("branchName","")
    accountdetails.addProperty("upiNumber","")
    val json = JsonObject()
    try{
        json.addProperty("customerName",  saveAddressModel.customerName)
        json.addProperty("primaryPhonenumber", saveAddressModel.primaryPhonenumber)
        json.addProperty("addressLine",  saveAddressModel.addressLine)
        json.addProperty("zipcode",  saveAddressModel.zipcode)
        json.addProperty("city",  saveAddressModel.city)
        //json.addProperty("state",  json2)
        json.add("state", Gson().toJsonTree(json2))
        json.add("country",Gson().toJsonTree(countryjson))
        json.add("accountdetails",Gson().toJsonTree(accountdetails))
        json.addProperty("email","muthuking58@gmail.com")
        json.addProperty("secondaryPhonenumber",0)

        Log.d(TAG, "customerDetails: json check "+ json.toString())
    }catch (e: Exception) {
    }

    return json

}
/*

private  fun customerDetails(saveAddressModel: SaveAddressModel): JsonObject {
    var json2 = JsonObject()
    // json2.addproperty("stateName","Karnataka")
    json2.addProperty("stateName","karnataka")
    var countryjson= JSONObject()
    countryjson.addProperty("countryName","India")
    var accountdetails=JSONObject()
    accountdetails.addProperty("accountNumber","dsds")
    accountdetails.addProperty("ifscCode","sds")
    accountdetails.addProperty("branchName","sds")
    accountdetails.addProperty("upiNumber","dsdsd")
    val json = JsonObject()
    try{
        json.addProperty("customerName",  saveAddressModel.customerName)
        json.addProperty("primaryPhonenumber", saveAddressModel.primaryPhonenumber)
        json.addProperty("addressLine",  saveAddressModel.addressLine)
        json.addProperty("zipcode","")
        json.addProperty("city",  saveAddressModel.city)
        json.addProperty("state",  json2.toString())
        json.addProperty("country",countryjson.toString())
        json.addProperty("accountdetails","accountdetails.toString())
    }catch (e: Exception) {
    }
    return json
}


*/





