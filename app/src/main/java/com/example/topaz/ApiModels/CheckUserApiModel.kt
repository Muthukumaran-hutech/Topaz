package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class CheckUserApiModel {

    @SerializedName("customercode")
    public var customercode: String = ""

    @SerializedName("customerName")
    public var customerName: String = ""

    @SerializedName("primaryPhonenumber")
    public var primaryPhonenumber: String= ""

    @SerializedName("secondaryPhonenumber")
    public var secondaryPhonenumber: Long = 0

    @SerializedName("email")
    public var email: String = ""

    @SerializedName("addressLine")
    public var addressLine: String = ""

    @SerializedName("city")
    public var city: String = ""

    @SerializedName("state")
    public var state: StateData = StateData()

    @SerializedName("country")
    public var country: countryData2 = countryData2()


    @SerializedName("zipcode")
    public var zipcode: Int = 0

    @SerializedName("accountdetails")
    public var accountdetails: AccountDetails = AccountDetails()

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






}

class AccountDetails {

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

class countryData2 {
    @SerializedName("countryName")
    public var countryName: String = ""
}

class StateData {

    @SerializedName("stateId")
    public var stateId: Int = 0

    @SerializedName("stateName")
    public var stateName: String = ""

    @SerializedName("country")
    public var country: countryData = countryData()


}

class countryData {

    @SerializedName("countryName")
    public var countryName: String = ""


}
