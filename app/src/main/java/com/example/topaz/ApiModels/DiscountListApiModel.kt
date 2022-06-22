package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class DiscountListApiModel {
    @SerializedName("discountid")
    public var discountId:Int?=0

    @SerializedName("productName")
    public  var productdetail:ProductDetails?= ProductDetails()

    @SerializedName("squarefeetprice")
    public var squareFeetPrice:Double?=.0

    @SerializedName("discount")
    public var discount:String?=""


}

class ProductDetails{
    @SerializedName("productid")
    public var productId:Int?=0


    @SerializedName("productTitle")
    public var productTitle:String?=""

}