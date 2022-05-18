package com.example.topaz.Activities

import android.app.Activity
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
import com.google.gson.JsonObject
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
        //var custEmailId = sharedPreference.getString("email", "")
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

        binding.saveAddressBtn?.setOnClickListener {
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
                }else{
                    Log.d("Update Res fail:", response.body()!!.message)
                }

            }

            override fun onFailure(call: Call<UpdateUserApiModel?>, t: Throwable) {
                t.message?.let { Log.d("Update Res failure:", it) }
            }
        })

    }
}

private  fun customerDetails(saveAddressModel: SaveAddressModel): JsonObject {
    val json = JsonObject()
    try{
        json.addProperty("customerName",  saveAddressModel.customerName)
        json.addProperty("primaryPhonenumber", saveAddressModel.primaryPhonenumber)
        json.addProperty("addressLine",  saveAddressModel.addressLine)
        json.addProperty("zipcode",  saveAddressModel.zipcode)
        json.addProperty("city",  saveAddressModel.city)
        json.addProperty("stateName",  saveAddressModel.stateName)

    }catch (e: Exception) {
    }

    return json

}
