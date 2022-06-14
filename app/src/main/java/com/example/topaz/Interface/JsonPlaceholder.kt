package com.example.topaz.Interface

import com.example.topaz.ApiModels.*
import com.google.gson.JsonObject
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

// for home page
    @GET("viewCategory")
    fun viewCategory(): Call<List<CategoryListApiModel>>

    // for category page
    @GET("viewCategory/{CategoryID}")
    fun viewCategoryItems( @Path("CategoryID") catId: String): Call<List<CategoryListApiModel>>

    @GET("all/advertisementlist")
    fun viewAdvertisement(): Call<List<AdvertisementApiModel>>



    @GET("viewProdcutBySubCategoryId/{subid}")
    fun subCategory( @Path("subid") subid: String): Call<List<SubCategoryApiModel>>

    @GET("viewProductDetails/{productId}")
    fun viewProduct(@Path("productId") productId: String): Call<ProductDetailsListApiModel>

    @GET("viewProduct")
    fun viewProductList(): Call<List<ProductDetailsListApiModel>>

    @GET("offers/get")
    fun viewOffers(): Call<List<OffersListApiModel>>

 /*   @GET("myorder/ORD001")
    fun alertNotify():Call<List<ViewOrderApimodel>>*/

    @GET("myorder/{customerId}")
    fun alertNotify(@Path("customerId") customerId: String):Call<List<AlertApiModel>>

    @GET("myorder/{customerId}")
    fun viewOders(@Path("customerId") customerId: String):Call<List<ViewOrderApimodel>>


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

    //Get subcategory
    @GET("getSubCategoryByCategoryName/{categoryName}")
   public fun getSubcategoryList(@Path("categoryName") categoryName:String):Call<List<SubCategoryListApiModel>>



}
