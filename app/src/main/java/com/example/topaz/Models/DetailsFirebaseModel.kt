package com.example.topaz.Models

data class DetailsFirebaseModel (
    var custId: String = "",
    var productImage: String = "",
    var productid: Int = 0,
    var productTitle: String = "",
    var thickness: String = "",
    var price: Int = 0,
    var productDiscountId: String = "",
    var addedToWishList:Boolean=true
        )