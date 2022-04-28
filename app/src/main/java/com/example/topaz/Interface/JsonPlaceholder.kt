package com.example.topaz.Interface

import com.example.topaz.Models.UpdateUserApiModel
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface JsonPlaceholder {
    /*@PUT("updatecustomer/CUS001")//With using suspend function
   suspend fun updateInfo(@Body jsonObject: JsonObject):UpdateUserApiModel
*/
    @PUT("updatecustomer/CUS001")//Without suspend funaction
    fun updateInfo(@Body jsonObject: JsonObject):Call<UpdateUserApiModel>
}
