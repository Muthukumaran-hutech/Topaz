package com.example.topaz.Activities

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
    var custEmailId:String?=""
    lateinit var sharedPreference:SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)


        activity = this
        setSupportActionBar(binding.editAddressToolbar)
        supportActionBar?.title = ""

        sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        var custName = sharedPreference.getString("customerName", "")
        var custId = sharedPreference.getString("customercode", "")
        var custAddress = sharedPreference.getString("addressLine", "")
        var custPhoneno = sharedPreference.getString("primaryPhonenumber", "")
         custEmailId = sharedPreference.getString("email", "")
        var custCity = sharedPreference.getString("city", "")
        var custState = sharedPreference.getString("state", "")
        var zipcode = sharedPreference.getString("zipcode","")

        binding.nameAddress.setText(custName)
        binding.phoneNoChange.setText(custPhoneno)
        binding.locality.setText(custAddress)
        binding.houseNo.setText(custAddress)
        binding.city.setText(custCity)
        binding.state.setText(custState)
        binding.pincode.setText(zipcode)


        binding.categoryBackArrow.setOnClickListener {
            finish()
        }

        binding.saveAddressBtn.setOnClickListener {

            if(validateFields()) {
                updateuserinfo()
            }
        }



    }

    private fun updateuserinfo() {

        try {
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
            val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
            var custId = sharedPreference.getString("customercode", "")
            res.saveAddress(custId!!, customerDetails(saveAddressModel))
                .enqueue(object : Callback<UpdateUserApiModel?> {
                    override fun onResponse(
                        call: Call<UpdateUserApiModel?>,
                        response: Response<UpdateUserApiModel?>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("Update Res success:", response.body()!!.message)
                            Toast.makeText(
                                applicationContext,
                                "Details Updated Sucessfully",
                                Toast.LENGTH_LONG
                            )
                                .show()

                            storeDataLocally()
                            finish()
                        } else {
                             Log.d("Update Res fail:", response.body()!!.message)
                        }

                    }

                    override fun onFailure(call: Call<UpdateUserApiModel?>, t: Throwable) {
                        t.message?.let {
                            Log.d("Update Res failure:", it) }
                    }
                })

        }
        catch (e:Exception){
            e.toString()
        }
    }

    fun validateFields():Boolean
    {
        var isValid=true
        if(binding.city.text.toString().isEmpty()){
            isValid=false
            Toast.makeText(this,"City cannot be empty",Toast.LENGTH_LONG).show()
        }
        else if(binding.pincode.text.toString().isEmpty()){
            isValid=false
            Toast.makeText(this,"Pincode cannot be empty",Toast.LENGTH_LONG).show()
        }

        else if(binding.state.text.toString().isEmpty()){
            isValid= false
            Toast.makeText(this,"State cannot be empty",Toast.LENGTH_LONG).show()
        }

        else if(binding.nameAddress.text.toString().isEmpty()){
            isValid=false
            Toast.makeText(this,"Name field cannot be empty",Toast.LENGTH_LONG).show()
        }

        return isValid

    }



    private  fun customerDetails(saveAddressModel: SaveAddressModel): JsonObject {
        var json2 = JsonObject()
        // json2.addproperty("stateName","Karnataka")
        json2.addProperty("stateName",saveAddressModel.stateName)
        var countryjson= JsonObject()
        countryjson.addProperty("countryName","India")
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
            json.addProperty("email", custEmailId)
            json.addProperty("secondaryPhonenumber",0)

            Log.d(TAG, "customerDetails: json check "+ json.toString())
        }catch (e: Exception) {
        }

        return json

    }


    private fun storeDataLocally(){
        var editor=sharedPreference.edit()
        editor.putString("customerName",binding.nameAddress.text.toString())
        editor.putString("addressLine",binding.locality.text.toString())
        editor.putString("primaryPhonenumber",binding.phoneNoChange.text.toString())
        editor.putString("zipcode",binding.pincode.text.toString())
        editor.putString("city",binding.city.text.toString())
        editor.putString("state",binding.state.text.toString())
        editor.apply()
    }



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





