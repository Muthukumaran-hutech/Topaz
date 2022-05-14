package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class ChangeEmailOtpApiModel {
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
    public var state: StateData2 = StateData2()

    @SerializedName("country")
    public var country: countryData3 = countryData3()


    @SerializedName("zipcode")
    public var zipcode: Int = 0



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




class countryData3 {
    @SerializedName("countryName")
    public var countryName: String = ""
}

class StateData2 {

    @SerializedName("stateId")
    public var stateId: Int = 0

    @SerializedName("stateName")
    public var stateName: String = ""

    @SerializedName("country")
    public var country: countryData4 = countryData4()


}

class countryData4 {

    @SerializedName("countryName")
    public var countryName: String = ""


}
