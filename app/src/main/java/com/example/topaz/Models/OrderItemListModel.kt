package com.example.topaz.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class OrderItemListModel(
    public var orderItemId: Long? = 0,
    public var quantity: Int? = 0,
    public var sqftprice: Double? = .0,
    public var productTitle:String?="",
    public var price:Double?=.0,
    var productImage:String?="",
    var actualsqftprice: Double? =.0,
    var size: String?=""


):Parcelable {
}