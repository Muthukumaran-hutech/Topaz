package com.example.topaz.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
 data class ProductDetailsModel(
 var ProductId : Int = 0,
 var productImage: String = "",
 var ProductTitle: String = "",
 var ProductPrice: String = "",
 var ProductSize: String = "",
 var ProductThickness: String = "",
 var ProductBrand: String = "",
 var ProductWoodType: String = "",
   // var ProductSupplier: String = "",
 var ProductDescription: String = ""): Parcelable