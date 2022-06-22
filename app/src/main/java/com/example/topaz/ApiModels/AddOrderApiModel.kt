package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class AddOrderApiModel {

    @SerializedName("status")
    var status:Int?=0

    @SerializedName("message")
    var message:String?=""
}