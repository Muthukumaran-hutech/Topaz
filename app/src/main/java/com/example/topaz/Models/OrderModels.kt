package com.example.topaz.Models

import com.example.topaz.ApiModels.ItmOrdr
class OrderModels(
    var image: String = "",
    var QuotationStatus: String = "",
    var quotationID: String = "",
    var quotationDate: String = "",
    var quotationquantity: Int = 0,
    var quotationThickness: String = "",
    var quotationSqftPrice: Double = .0,
    var quotationTitle: String? = "",
    var quotationPaymentMode: String = "",
    var quotationAddressLine: String = "",
    var amountWithTax:Double=.0,
    var amountWithoutTax:Double = .0,
    var taxAmount:Double =.0,
    var actualSqFeetPrice:Double? = .0,
    var size:String?="",
    var quantity:Int?=0,
    var orderitemlist:java.util.ArrayList<ItmOrdr> = ArrayList<ItmOrdr>()
)