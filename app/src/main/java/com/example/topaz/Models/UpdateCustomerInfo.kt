package com.example.topaz.Models

data class UpdateCustomerInfo(

    var customerName: String = "",
    var customerId: String = "",
    var customerAddress: String = "",
    var customerPhoneNo: String = "",
    var customerEmailAddress: String = "")