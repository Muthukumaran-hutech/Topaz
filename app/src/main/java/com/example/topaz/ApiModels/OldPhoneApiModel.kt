package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class OldPhoneApiModel {

    @SerializedName("balance")
    public var balance: Int = 0

    @SerializedName("batch_id")
    public var batch_id: Long = 0

    @SerializedName("cost")
    public var cost: Int = 0

    @SerializedName("num_messages")
    public var num_messages6: Int = 0


    @SerializedName("message")
    public var message: messageData = messageData()

    @SerializedName("receipt_url")
    public var receipt_url: String = ""

    @SerializedName("custom")
    public var custom: String = ""

    @SerializedName("messages")
    public var messages: List<messageData0> =  ArrayList()

    @SerializedName("status")
    public var status: String = ""

}

class messageData0 {
    @SerializedName("id")
    public var id: String = ""

    @SerializedName("recipient")
    public var recipient: Long = 0
}

class messageData {
    @SerializedName("num_parts")
    public var num_parts: Int = 0

    @SerializedName("sender")
    public var sender: String = ""

    @SerializedName("content")
    public var content: String = ""
}

