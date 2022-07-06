package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class AdvertisementApiModel {


    @SerializedName("advertisementid")
    public var advertisementid: Int = 0

    @SerializedName("title")
    public var title: String = ""

    @SerializedName("discription")
    public var discription: String? = ""

    @SerializedName("selectcustomers")
    public var selectcustomers: ArrayList<Addcust> = ArrayList<Addcust>()

    @SerializedName("advertismentImage")
    public var advertismentImage : Addverse = Addverse()

}

class Addverse {
    @SerializedName("imageId")
    public var imageId: Int = 0

    @SerializedName("advertisementid")
    public var advertisementid: Long = 0

    @SerializedName("imagepath")
    public var imagepath: String = ""

    @SerializedName("imagebyte")
    public var imagebyte: String = ""

    @SerializedName("creationTime")
    public var creationTime: String = ""


}

class Addcust {

    @SerializedName("customercode")
    public var customercode: String = ""

    @SerializedName("customerName")
    public var customerName: String = ""

  /*  @SerializedName("primaryPhonenumber")
    public var primaryPhonenumber: String = ""

    @SerializedName("secondaryPhonenumber")
    public var secondaryPhonenumber: Int = 0*/

    @SerializedName("email")
    public var email: String = ""

    @SerializedName("addressLine")
    public var addressLine: String = ""

    @SerializedName("city")
    public var city: String = ""

    @SerializedName("state")
    public var state: CitySt = CitySt()

    @SerializedName("country")
    public var country: CnNm = CnNm()


    @SerializedName("zipcode")
    public var zipcode: Long = 0

    @SerializedName("accountdetails")
    public var accountdetails: Acdtl = Acdtl()

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

class Acdtl {

    @SerializedName("accountid")
    public var accountid: Long = 0

    @SerializedName("accountNumber")
    public var accountNumber: String = ""

    @SerializedName("ifscCode")
    public var ifscCode: String = ""

    @SerializedName("branchName")
    public var branchName: String = ""

    @SerializedName("upiNumber")
    public var upiNumber: String = ""

}

class CnNm {
    @SerializedName("countryName")
    public var countryName: String = ""

}

class CitySt {

    @SerializedName("stateId")
    public var stateId: Long = 0

    @SerializedName("stateName")
    public var stateName: String = ""

    @SerializedName("country")
    public var country: Cnry = Cnry()

}

class Cnry {
    @SerializedName("countryName")
    public var countryName: String = ""

}
