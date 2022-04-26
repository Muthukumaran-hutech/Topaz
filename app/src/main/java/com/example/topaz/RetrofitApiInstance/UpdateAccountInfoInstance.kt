package com.example.topaz.RetrofitApiInstance

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpdateAccountInfoInstance {

    companion object{
        val baseUrl = "https://hutech-topaz.herokuapp.com/"
        fun getUpdateAccountInfoInstance(): Retrofit {
//--------------------//
           return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}