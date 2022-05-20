package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class QuotationApiModel {
    @SerializedName("status")
    public var status:String=""

    @SerializedName("message")
    public var message:String=""
}