package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class ChangeNewEmailOtpApiModel {

    @SerializedName("status")
    public var status: Int = 0

    @SerializedName("message")
    public var message: String = ""
}