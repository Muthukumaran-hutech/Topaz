package com.example.topaz.Models

class AskQuotationModel(
    var customerConversation: String = "",
    var customerQuantity: Int = 0,
    var customerSize: String = "",
    var customerThickness: String = "",
    var customerSqftPrice: Double = .0,
    var customerProductId: Int = 0,
    var customerPrice: Double = .0,
    var shippinghandling: Double = .0,
    var amountwithtax: Double = .0,
    var amountwithouttax: Double = .0,
    var taxtype :String = "GST",
    var taxpercentage :String = "18%",
    var taxamount : String = "",
    var discountid : String ="1",
    var discountstatus : String = "",
    var exdiscount :String = ""

    )