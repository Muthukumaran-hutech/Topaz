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
    fun updateInfo(
        @Path("customerId") custId: String,
        @Body jsonObject: JsonObject
    ): Call<UpdateUserApiModel>


    @GET("viewCategory")
    fun viewCategory(): Call<List<CategoryListApiModel>>

    @GET("viewProdcutBySubCategoryId/{subid}")
    fun subCategory( @Path("subid") subid: String): Call<List<SubCategoryApiModel>>

    @GET("viewProduct/{subid}")
    fun viewProduct(@Path("subid") subid: String): Call<List<ProductDetailsListApiModel>>


    @GET("offers/get")
    fun viewOffers(): Call<List<OffersListApiModel>>

    @GET("orderlist/ORD001")
    fun alertNotify():Call<AlertApiModel>


    @POST("customerdetails")
    @Multipart
    fun checkIfCustomerExists(@Part("primaryPhonenumber") requestBody: RequestBody): Call<CheckUserApiModel>

    @PUT("send/otp/{customerId}")//Without suspend function
    fun verifyOldEmail(
        @Path("customerId") customerID: String,
        @Body jsonObject: JsonObject
    ): Call<ChangeEmailOtpApiModel>

    @PUT("verify/customer/otp")
    @Multipart//Without suspend function
    fun verifyOldEmailOtp(@Part("emailOtp") requestBody: RequestBody): Call<ChangeEmailOtpVerifyApiModel>

    @PUT("update/customer/email/{emailOtp}")
    @Multipart
    fun verifyNewEmail(
        @Path("emailOtp") emailOtp: String,
        @Part("email") requestBody: RequestBody
    ): Call<VerifyNewEmailApiModel>

    @PUT("verify/customer/update/otp")
    @Multipart//Without suspend function
    fun verifyNewEmailOtp(@Part("emailOtp") requestBody: RequestBody): Call<ChangeNewEmailOtpApiModel>


    @POST("sendotp/number/{customerId}")
    @Multipart
    fun verifyOldPhoneNumber(
        @Path("customerId") customerID: String,
        @Part("phoneNumber") requestBody: RequestBody
    ): Call<OldPhoneApiModel>

    @POST("verifyOldphoneNumber/{customerId}")
    @Multipart
    fun verifyoldPhoneOtp(
        @Path("customerId") customerID: String,
        @Part("phoneotp") requestBody: RequestBody
    ): Call<ChangeOldPhoneOtpApiModel>


    @POST("sendOTPToNewNumber/{customerId}")
    @Multipart
    fun verifyNewPhoneNumber( @Path("customerId") customerID: String,
                              @Part("phoneNumber") requestBody: RequestBody
    ): Call<OldPhoneApiModel>

    @POST("verifyNewPhoneNumber/{customerId}")
    @Multipart
    fun verifyNewPhoneOtp( @Path("customerId") customerID: String,
                              @Part("phoneotp") requestBody: RequestBody
    ): Call<ChangeNewEmailOtpApiModel>

    @PUT("updatecustomer/{customerId}")//Without suspend function
    fun saveAddress(
        @Path("customerId") customerId: String,
        @Body jsonObject: JsonObject
    ): Call<UpdateUserApiModel>

    @POST("getPrice/onOrder")
    fun askQotation(
        @Body jsonObject: JsonObject
    ): Call<QuotationApiModel>

}
