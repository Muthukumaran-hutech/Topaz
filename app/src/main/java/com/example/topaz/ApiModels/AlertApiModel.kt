package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class AlertApiModel {


    @SerializedName("orderid")
    public var orderid: String = ""

    @SerializedName("createdDate")
    public var createdDate: String = ""


    @SerializedName("amountwithtax")
    public var amountwithtax: Double = .0

    @SerializedName("amountwithouttax")
    public var amountwithouttax: Double = .0

    @SerializedName("orderTax")
    public var orderTax: String = ""

    @SerializedName("orderstatus")
    public var orderstatus: Ostat = Ostat()

    @SerializedName("customer")
    public var customer: Cdet = Cdet()

}

class Cdet {
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

    @SerializedName("state")
    public var state: Statedt = Statedt()

    @SerializedName("country")
    public var country: Cnty = Cnty()

    @SerializedName("zipcode")
    public var zipcode: Long = 0

    @SerializedName("accountdetails")
    public var accountdetails: Accdet = Accdet()

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

    @SerializedName("paymentmode")
    public var paymentmode: String = ""

    @SerializedName("orderItems")
    public var orderItems: ArrayList<ItemCat> = ArrayList<ItemCat>()


}

class Accdet {

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

class ItemCat {

    @SerializedName("orderItemId")
    public var orderItemId: Int = 0

    @SerializedName("quantity")
    public var quantity: Long = 0

    @SerializedName("price")
    public var price: Double = .0

    @SerializedName("size")
    public var size: String = ""

    @SerializedName("thickness")
    public var thickness: String = ""

    @SerializedName("sqftprice")
    public var sqftprice: String = ""

    @SerializedName("customerconversation")
    public var customerconversation: String = ""

    @SerializedName("manufacturerconversation")
    public var manufacturerconversation: String = ""

    @SerializedName("product")
    public var product: pro = pro()

}

class pro {

    @SerializedName("productid")
    public var productid: Int = 0

    @SerializedName("productTitle")
    public var productTitle: String = ""

    @SerializedName("categoryType")
    public var categoryType: ctaType = ctaType()


}

class ctaType {

    @SerializedName("categoryid")
    public var categoryid: String = ""

    @SerializedName("categoryName")
    public var categoryName: String = ""

    @SerializedName("tags")
    public var tags: ArrayList<tas> = ArrayList<tas>()

    @SerializedName("subcategory")
    public var subcategory: ArrayList<Subct> = ArrayList<Subct>()

    @SerializedName("subid")
    public var subid: String = ""

    @SerializedName("discription")
    public var discription: String = ""

    @SerializedName("createdDate")
    public var createdDate: String = ""

    @SerializedName("manufacturerCode")
    public var manufacturerCode: String = ""

    @SerializedName("categoryimage")
    public var categoryimage: ctaimG = ctaimG()

    @SerializedName("active")
    public var active: String = ""


}

class ctaimG {

    @SerializedName("imageId")
    public var imageId: Int = 0


    @SerializedName("categoryid")
    public var categoryid: String = ""


    @SerializedName("imagepath")
    public var imagepath: String = ""

    @SerializedName("imagebyte")
    public var imagebyte: String = ""


    @SerializedName("creationTime")
    public var creationTime: String = ""

}

class Subct {
    @SerializedName("subid")
    public var subid: Int = 0


    @SerializedName("subcategory")
    public var subcategory: String = ""


    @SerializedName("categoryid")
    public var categoryid: String = ""


    @SerializedName("categoryName")
    public var categoryName: String = ""

}

class tas {
    @SerializedName("tags")
    public var tags: String = ""

}

class Cnty {
    @SerializedName("countryName")
    public var countryName: String = ""
}

class Statedt {
    @SerializedName("stateId")
    public var stateId: Int = 0

    @SerializedName("stateName")
    public var stateName: String = ""

    @SerializedName("country")
    public var country: Cntrynm = Cntrynm()
}

class Cntrynm {
    @SerializedName("countryName")
    public var countryName: String = ""
}

class Ostat {
    @SerializedName("statusid")
    public var statusid: Int = 0

    @SerializedName("status")
    public var status: String = ""

}
