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
    public var orderstatus: Ostat1 = Ostat1()

    @SerializedName("customer")
    public var customer: Cdet = Cdet()

    @SerializedName("paymentmode")
    public var paymentmode: String = ""

    @SerializedName("orderItems")
    public var orderItems: ArrayList<ItmOrdr1> = ArrayList<ItmOrdr1>()

}

class ItmOrdr1 {

    @SerializedName("orderItemId")
    public var orderItemId: Long = 0

    @SerializedName("quantity")
    public var quantity: Long = 0

   /* @SerializedName("price")
    public var price: String = ""

    @SerializedName("size")
    public var size: String = ""

    @SerializedName("thickness")
    public var thickness: String = ""*/

    @SerializedName("sqftprice")
    public var sqftprice: Double = .0

    @SerializedName("customerconversation")
    public var customerconversation: String = ""

    @SerializedName("manufacturerconversation")
    public var manufacturerconversation: String = ""

    @SerializedName("product")
    public var product: PROD = PROD()
    ///

    @SerializedName("minOrderQty")
    public var minOrderQty: Long = 0

    @SerializedName("maxOrderQty")
    public var maxOrderQty: Long = 0

    @SerializedName("color")
    public var color: String = ""

    @SerializedName("woodType")
    public var woodType: String = ""

    @SerializedName("unitMeasure")
    public var unitMeasure: String = ""

    @SerializedName("size")
    public var size: String = ""

    @SerializedName("thickness")
    public var thickness: String = ""

    @SerializedName("brand")
    public var brand: String = ""

    @SerializedName("sqFeetPrice")
    public var sqFeetPrice: Double = .0

    @SerializedName("price")
    public var price: Double = .0

    @SerializedName("stock")
    public var stock: Long = 0

    @SerializedName("threshold")
    public var threshold: Long = 0

    @SerializedName("collection")
    public var collection: String = ""

    @SerializedName("currency")
    public var currency: String = ""

    @SerializedName("productImage")
    public var productImage: String = ""

    @SerializedName("discription")
    public var discription: String = ""

    @SerializedName("tag")
    public var tag: String = ""

    @SerializedName("createdDate")
    public var createdDate: String = ""

    @SerializedName("productimage")
    public var productimage: PIM1 = PIM1()

    @SerializedName("manufacturerCode")
    public var manufacturerCode: Boolean = true

    @SerializedName("active")
    public var active: String = ""

    @SerializedName("sku")
    public var sku: String = ""

    @SerializedName("orderDiscount")
    public var orderDiscount: OrDIS = OrDIS()






}

class OrDIS {

    @SerializedName("orderdiscountid")
    public var orderdiscountid: Long = 0

    @SerializedName("orderid")
    public var orderid: String = ""

    @SerializedName("orderItemId")
    public var orderItemId: Long = 0

    @SerializedName("discount")
    public var discount: Detaildiscount = Detaildiscount()


    @SerializedName("discountstatus")
    public var discountstatus: String = ""

    @SerializedName("exdiscount")
    public var exdiscount: String = ""

    @SerializedName("promotionsdiscounts")
    public var promotionsdiscounts: Double = .0


}

class Detaildiscount {

    @SerializedName("discountid")
    public var discountid: Long = 0

    @SerializedName("productName")
    public var productName: String = ""

    @SerializedName("size")
    public var size: String = ""

    @SerializedName("stock")
    public var stock: Long = 0

    @SerializedName("thickness")
    public var thickness: String = ""

    @SerializedName("squarefeetprice")
    public var squarefeetprice: Double = .0

    @SerializedName("quantity")
    public var quantity: Long = 0

    @SerializedName("totalprice")
    public var totalprice: Double = .0

    @SerializedName("gstamount")
    public var gstamount: Double = .0

    @SerializedName("discountype")
    public var discountype: String = ""

    @SerializedName("discount")
    public var discount: String = ""

    @SerializedName("selectcustomers")
    public var selectcustomers: ArrayList<SLCUS> = ArrayList<SLCUS>()

}

class SLCUS {

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
    public var state: STDLC = STDLC()


}

class STDLC {

    @SerializedName("stateId")
    public var stateId: Int = 0

    @SerializedName("stateName")
    public var stateName: String = ""

    @SerializedName("country")
    public var country: CntrynmE123 = CntrynmE123()

    @SerializedName("countryName")
    public var countryName: String = ""

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

}

class CntrynmE123 {
    @SerializedName("countryName")
    public var countryName: String = ""

}

class PIM1 {
    @SerializedName("imageId")
    public var imageId: Long = 0

    @SerializedName("productid")
    public var productid: Long = 0

    @SerializedName("imagepath")
    public var imagepath: String = ""

    @SerializedName("imagebyte")
    public var imagebyte: String = ""

    @SerializedName("creationTime")
    public var creationTime: String = ""

}

class PROD {

    @SerializedName("productid")
    public var productid: Long = 0

    @SerializedName("productTitle")
    public var productTitle: String = ""

    @SerializedName("categoryType")
    public var categoryType: CType = CType()




}

class CType {
    @SerializedName("categoryid")
    public var categoryid: String = ""

    @SerializedName("categoryName")
    public var categoryName: String = ""

    @SerializedName("tags")
    public var tags: ArrayList<TS1> = ArrayList<TS1>()

    @SerializedName("subcategory")
    public var subcategory: ArrayList<SUCTy> = ArrayList<SUCTy>()

    @SerializedName("subid")
    public var subid: String = ""

    @SerializedName("discription")
    public var discription: String = ""

    @SerializedName("createdDate")
    public var createdDate: String = ""

    @SerializedName("manufacturerCode")
    public var manufacturerCode: String = ""

    @SerializedName("categoryimage")
    public var categoryimage: CIM = CIM()

    @SerializedName("active")
    public var active: String = ""



}

class CIM {

    @SerializedName("imageId")
    public var imageId: Long = 0

    @SerializedName("categoryid")
    public var categoryid: String = ""

    @SerializedName("imagepath")
    public var imagepath: String = ""

    @SerializedName("imagebyte")
    public var imagebyte: String = ""

    @SerializedName("creationTime")
    public var creationTime: String = ""

}

class SUCTy {

    @SerializedName("subid")
    public var subid: Long = 0

    @SerializedName("subcategory")
    public var subcategory: String = ""

    @SerializedName("categoryid")
    public var categoryid: String = ""

    @SerializedName("categoryName")
    public var categoryName: String = ""

}

class TS1 {
    @SerializedName("tags")
    public var tags: String = ""

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
    public var state: Statedt3 = Statedt3()

    @SerializedName("country")
    public var country: Cnty1 = Cnty1()

    @SerializedName("zipcode")
    public var zipcode: Long = 0

    @SerializedName("accountdetails")
    public var accountdetails: Accdet1 = Accdet1()

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

    @SerializedName("paymentstatus")
    public var paymentstatus: String = ""

    @SerializedName("prioritystatus")
    public var prioritystatus: PRISTS = PRISTS()

    @SerializedName("manufacturerCode")
    public var manufacturerCode: String = ""

    @SerializedName("shippinghandling")
    public var shippinghandling: Double = .0


}

class PRISTS {

    @SerializedName("prioritystatusid")
    public var prioritystatusid: Long = 0

    @SerializedName("prioritystatus")
    public var prioritystatus: String = ""

}

class Accdet1 {

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
    public var tags: ArrayList<tas1> = ArrayList<tas1>()

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

class tas1 {
    @SerializedName("tags")
    public var tags: String = ""

}

class Cnty1 {
    @SerializedName("countryName")
    public var countryName: String = ""
}

class Statedt3 {
    @SerializedName("stateId")
    public var stateId: Int = 0

    @SerializedName("stateName")
    public var stateName: String = ""

    @SerializedName("country")
    public var country: Cntrynm2 = Cntrynm2()
}

class Cntrynm2 {
    @SerializedName("countryName")
    public var countryName: String = ""
}

class Ostat1 {
    @SerializedName("statusid")
    public var statusid: Int = 0

    @SerializedName("status")
    public var status: String = ""

}

