package com.example.topaz.Interface

import com.example.topaz.ApiModels.CategoryListApiModel
import com.example.topaz.ApiModels.ProductDetailsListApiModel
import com.example.topaz.ApiModels.UpdateUserApiModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

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


}
