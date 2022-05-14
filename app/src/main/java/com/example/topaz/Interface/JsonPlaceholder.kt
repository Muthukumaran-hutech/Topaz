package com.example.topaz.Interface

import com.example.topaz.ApiModels.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceholder {
    /*@PUT("updatecustomer/CUS001")//With using suspend function
   suspend fun updateInfo(@Body jsonObject: JsonObject):UpdateUserApiModel
*/
    @PUT("updatecustomer/{customerId}")//Without suspend function
    fun updateInfo( @Path("customerId") custId:String,@Body jsonObject: JsonObject):Call<UpdateUserApiModel>


    @GET("viewCategory")
    fun  viewCategory():Call<List<CategoryListApiModel>>

    @GET("viewProduct")
    fun  viewProduct():Call<List<ProductDetailsListApiModel>>


    @GET("offers/get")
    fun  viewOffers():Call<List<OffersListApiModel>>


    @POST("customerdetails")
    @Multipart
    fun checkIfCustomerExists(@Part("primaryPhonenumber") requestBody: RequestBody):Call<CheckUserApiModel>

    @PUT("send/otp/{customerId}")//Without suspend function
    fun verifyOldEmail(@Path("customerId") customerID:String, @Body jsonObject: JsonObject):Call<ChangeEmailOtpApiModel>

    @PUT("verify/customer/otp")
    @Multipart//Without suspend function
    fun verifyOldEmailOtp(@Part("emailOtp")requestBody: RequestBody):Call<ChangeEmailOtpVerifyApiModel>

   /* @PUT("update/customer/email/{email}")
    fun verifyNewEmail(@Path("email")emailOtp:String, @Body jsonObject: JsonObject) : Call<VerifyNewEmailApiModel>
*/
}
