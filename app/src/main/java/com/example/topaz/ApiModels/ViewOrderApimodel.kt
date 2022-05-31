package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class ViewOrderApimodel {

    @SerializedName("orderid")
    public var orderid: String = ""

    @SerializedName("createdDate")
    public var createdDate: String = ""

    @SerializedName("amountwithtax")
    public var amountwithtax: Double = .0

    @SerializedName("amountwithouttax")
    public var amountwithouttax: Double = .0

    @SerializedName("orderTax")
    public var orderTax: TaX = TaX()

    @SerializedName("orderstatus")
    public var orderstatus: OrdrSts = OrdrSts()

    @SerializedName("customer")
    public var customer: CUsDls = CUsDls()

}

class CUsDls {

    @SerializedName("customercode")
    public var customercode: String = ""

    @SerializedName("customerName")
    public var customerName: String = ""

    @SerializedName("primaryPhonenumber")
    public var primaryPhonenumber: String = ""

    @SerializedName("secondaryPhonenumber")
    public var secondaryPhonenumber: String = ""

    @SerializedName("email")
    public var email: String = ""

    @SerializedName("addressLine")
    public var addressLine: String = ""

    @SerializedName("city")
    public var city: String = ""

    //

    @SerializedName("state")
    public var state: STSDls = STSDls()

    @SerializedName("country")
    public var country: CNRYDls = CNRYDls()

    @SerializedName("zipcode")
    public var zipcode: Long = 0

    @SerializedName("accountdetails")
    public var accountdetails: ACDls = ACDls()

    @SerializedName("emailOtp")
    public var emailOtp: String = ""

    @SerializedName("otpCreationDate")
    public var otpCreationDate: String = ""

    @SerializedName("otpExpiredDate")
    public var otpExpiredDate: String = ""

    @SerializedName("verificationToken")
    public var verificationToken: String = ""

    @SerializedName("enabled")
    public var enabled: Boolean = true

    @SerializedName("tokenCreationDate")
    public var tokenCreationDate: String = ""

    @SerializedName("tokenExpiredDate")
    public var tokenExpiredDate: String = ""

    @SerializedName("phoneotp")
    public var phoneotp: Long = 0

    @SerializedName("phonestatus")
    public var phonestatus: String = ""




}

class ACDls {

    @SerializedName("accountid")
    public var accountid: Int = 0

    @SerializedName("accountNumber")
    public var accountNumber: String = ""

    @SerializedName("ifscCode")
    public var ifscCode: String = ""

    @SerializedName("branchName")
    public var branchName: String = ""

    @SerializedName("upiNumber")
    public var upiNumber: String = ""

}

class CNRYDls {
    @SerializedName("countryName")
    public var countryName: String = ""

}

class STSDls {
    @SerializedName("stateId")
    public var stateId: Int = 0

    @SerializedName("stateName")
    public var stateName: String = ""

    @SerializedName("country")
    public var country: CNmes = CNmes()

}

class CNmes {

    @SerializedName("countryName")
    public var countryName: String = ""

}

class OrdrSts {
    @SerializedName("statusid")
    public var statusid: Long = 0

    @SerializedName("status")
    public var status: String = ""

}

class TaX {

    @SerializedName("ordertaxid")
    public var ordertaxid: Long = 0

    @SerializedName("orderid")
    public var orderid: String = ""

    @SerializedName("taxtype")
    public var taxtype: String = ""

    @SerializedName("taxpercentage")
    public var taxpercentage: String = ""

    @SerializedName("taxamount")
    public var taxamount: Double = .0

}
