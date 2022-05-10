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
    @PUT("updatecustomer/CUS001")//Without suspend function
    fun updateInfo(@Body jsonObject: JsonObject):Call<UpdateUserApiModel>


    @GET("viewCategory")
    fun  viewCategory():Call<List<CategoryListApiModel>>

    @GET("viewProduct")
    fun  viewProduct():Call<List<ProductDetailsListApiModel>>


    @GET("offers/get")
    fun  viewOffers():Call<List<OffersListApiModel>>


    @POST("customerdetails")
    @Multipart
    fun checkIfCustomerExists(@Part("primaryPhonenumber") requestBody: RequestBody):Call<CheckUserApiModel>



}
