package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class ProductImageModel {
    @SerializedName("imageid")
    public var imageId:Int?=0

    @SerializedName("productid")
    var productid: Int?=0

    @SerializedName("imagebyte")
    var imagebyte:String?=""
}