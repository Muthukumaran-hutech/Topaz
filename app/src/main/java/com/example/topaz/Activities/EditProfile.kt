package com.example.topaz.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.topaz.ApiModels.UpdateUserApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Interface.URIPathHelper
import com.example.topaz.Models.UpdateCustomerInfo
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityEditProfileBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_account_information.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class EditProfile : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    val REQUEST_CODE = 100
    lateinit var activity: Activity
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        var custName = sharedPreference.getString("customerName", "")
        var custEmailId = sharedPreference.getString("email", "")
        var custPhoneno = sharedPreference.getString("primaryPhonenumber", "")
        val custId = sharedPreference.getString("customercode","")
        val custAddress = sharedPreference.getString("addressLine","")


        binding.emailIdChange.setText(custEmailId)
        binding.phoneNoChange.setText(custPhoneno)
        binding.firstName.setText(custName)


        binding.emailIdChange.isEnabled = false
        binding.phoneNoChange.isEnabled = false



        binding.editImage.setOnClickListener {
            openGalleryForImage()
        }

        binding.change1.setOnClickListener {
            startActivity(Intent(activity, ChangeOldEmail::class.java))
            finish()
        }

        binding.change2.setOnClickListener {
            startActivity(Intent(activity, ChangeOldPhoneNumber::class.java))
            finish()
        }

        binding.resetBtn.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.update.setOnClickListener {

            if(validateFields()){
                var customerInfo=UpdateCustomerInfo(
                   customerId = custId!!,
                   customerName = first_name.text.toString(),
                   customerAddress = custAddress!!,
                   customerEmailAddress = email_id_change.text.toString(),
                   customerPhoneNo = phone_no_change.text.toString()
                )
                updateUserInfo(customerInfo)


            }

        }



        binding.backarrow1.setOnClickListener {
            startActivity(Intent(activity, MyAccount::class.java))
            finish()
        }
    }

    private fun openGalleryForImage() {

        database = FirebaseDatabase.getInstance().getReference("Profile")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"


        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            binding.editProfileImage.setImageURI(data?.data)
            val uriPathHelper = URIPathHelper()
         //   val filePath = uriPathHelper.getPath(this,)// handle chosen image
        }
    }

    fun updateUserInfo(customerInfo: UpdateCustomerInfo){
        binding.editprofileProgress.visibility=View.VISIBLE
        var updateuser=UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                      .create(JsonPlaceholder::class.java)

        updateuser.updateInfo(customerInfo.customerId,prepareUpdateRequestParams(customerInfo)).enqueue(object : Callback<UpdateUserApiModel?> {
            override fun onResponse(
                call: Call<UpdateUserApiModel?>,
                response: Response<UpdateUserApiModel?>
            ) {
                binding.editprofileProgress.visibility=View.GONE
                if(response.isSuccessful){
                 Toast.makeText(this@EditProfile,"Profile details updated successfully",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this@EditProfile,"Profile update failed",Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<UpdateUserApiModel?>, t: Throwable) {
                binding.editprofileProgress.visibility=View.GONE
                Toast.makeText(this@EditProfile,"Something went wrong. Update failed",Toast.LENGTH_LONG).show()
            }
        })


    }

    fun prepareUpdateRequestParams(updateCustomerInfo: UpdateCustomerInfo):JsonObject{
        var updateparams=JsonObject()
        try{
            updateparams.addProperty("customerID",  updateCustomerInfo.customerId)
            updateparams.addProperty("customerName", updateCustomerInfo.customerName)
            updateparams.addProperty("primaryPhonenumber",  updateCustomerInfo.customerPhoneNo)
            updateparams.addProperty("email",  updateCustomerInfo.customerEmailAddress)
            updateparams.addProperty("addressLine",  updateCustomerInfo.customerAddress)

        }
        catch (e:Exception){
            e.toString()
        }


        return updateparams

    }


    fun validateFields():Boolean{
        var isValid=true;
        if(first_name.text.toString().isEmpty() || first_name.text.toString()==""){
            isValid=false
            Toast.makeText(this,"First name cannot be empty",Toast.LENGTH_LONG).show()
        }
        else if(phone_no_change.text.toString().isEmpty() ||phone_no_change.text.toString()=="" ){
            isValid=false
            Toast.makeText(this,"Phone number cannot be empty",Toast.LENGTH_LONG).show()
        }
        else if(email_id_change.text.toString().isEmpty() || email_id_change.text.toString()==""){
            isValid=false
            Toast.makeText(this,"Email address cannot be empty",Toast.LENGTH_LONG).show()
        }



        return isValid

    }
}