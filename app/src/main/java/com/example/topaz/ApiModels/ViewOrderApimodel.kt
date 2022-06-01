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

  /*  @SerializedName("orderTax")
    public var orderTax: String = ""
*/
    @SerializedName("orderstatus")
    public var orderstatus: Ostat = Ostat()

    @SerializedName("customer")
    public var customer: Cdet21 = Cdet21()

    @SerializedName("paymentmode")
    public var paymentmode: String = ""

    @SerializedName("orderItems")
    public var orderItems: ArrayList<ItmOrdr> = ArrayList<ItmOrdr>()


}

class ItmOrdr {

    @SerializedName("orderItemId")
    public var orderItemId: Long = 0

    @SerializedName("quantity")
    public var quantity: Int = 0

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
    public var product: PROD1 = PROD1()
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
    public var productimage: PIM = PIM()

    @SerializedName("manufacturerCode")
    public var manufacturerCode: Boolean = true

    @SerializedName("active")
    public var active: String = ""

    @SerializedName("sku")
    public var sku: String = ""

    @SerializedName("orderDiscount")
    public var orderDiscount: OrDIS1 = OrDIS1()






}

class OrDIS1 {

    @SerializedName("orderdiscountid")
    public var orderdiscountid: Long = 0

    @SerializedName("orderid")
    public var orderid: String = ""

    @SerializedName("orderItemId")
    public var orderItemId: Long = 0

    @SerializedName("discount")
    public var discount: Detaildiscount1 = Detaildiscount1()


    @SerializedName("discountstatus")
    public var discountstatus: String = ""

    @SerializedName("exdiscount")
    public var exdiscount: String = ""

    @SerializedName("promotionsdiscounts")
    public var promotionsdiscounts: Double = .0


}

class Detaildiscount1{

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
    public var selectcustomers: ArrayList<SLCUS1> = ArrayList<SLCUS1>()

}

class SLCUS1 {

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
    public var state: STDLC1 = STDLC1()


}

class STDLC1 {

    @SerializedName("stateId")
    public var stateId: Int = 0

    @SerializedName("stateName")
    public var stateName: String = ""

    @SerializedName("country")
    public var country: CntrynmE = CntrynmE()

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

class CntrynmE {
    @SerializedName("countryName")
    public var countryName: String = ""

}

class PIM {
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

class PROD1 {

    @SerializedName("productid")
    public var productid: Long = 0

    @SerializedName("productTitle")
    public var productTitle: String = ""

    @SerializedName("categoryType")
    public var categoryType: CType1 = CType1()




}

class CType1 {
    @SerializedName("categoryid")
    public var categoryid: String = ""

    @SerializedName("categoryName")
    public var categoryName: String = ""

    @SerializedName("tags")
    public var tags: ArrayList<TS> = ArrayList<TS>()

    @SerializedName("subcategory")
    public var subcategory: ArrayList<SUCTy22> = ArrayList<SUCTy22>()

    @SerializedName("subid")
    public var subid: String = ""

    @SerializedName("discription")
    public var discription: String = ""

    @SerializedName("createdDate")
    public var createdDate: String = ""

    @SerializedName("manufacturerCode")
    public var manufacturerCode: String = ""

    @SerializedName("categoryimage")
    public var categoryimage: CIM1= CIM1()

    @SerializedName("active")
    public var active: String = ""



}

class CIM1 {

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

class SUCTy22 {

    @SerializedName("subid")
    public var subid: Long = 0

    @SerializedName("subcategory")
    public var subcategory: String = ""

    @SerializedName("categoryid")
    public var categoryid: String = ""

    @SerializedName("categoryName")
    public var categoryName: String = ""

}

class TS {
    @SerializedName("tags")
    public var tags: String = ""

}

class Cdet21 {
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
    public var state: Statedt1 = Statedt1()

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
    public var orderItems: ArrayList<ItemCat121> = ArrayList<ItemCat121>()

    @SerializedName("paymentstatus")
    public var paymentstatus: String = ""

    @SerializedName("prioritystatus")
    public var prioritystatus: PRISTS1 = PRISTS1()

    @SerializedName("manufacturerCode")
    public var manufacturerCode: String = ""

    @SerializedName("shippinghandling")
    public var shippinghandling: Double = .0


}

class PRISTS1 {

    @SerializedName("prioritystatusid")
    public var prioritystatusid: Long = 0

    @SerializedName("prioritystatus")
    public var prioritystatus: String = ""

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

class ItemCat121 {

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
    public var product: pro1 = pro1()

}

class pro1 {

    @SerializedName("productid")
    public var productid: Int = 0

    @SerializedName("productTitle")
    public var productTitle: String = ""

    @SerializedName("categoryType")
    public var categoryType: ctaType20 = ctaType20()


}

class ctaType20 {

    @SerializedName("categoryid")
    public var categoryid: String = ""

    @SerializedName("categoryName")
    public var categoryName: String = ""

    @SerializedName("tags")
    public var tags: ArrayList<tas> = ArrayList<tas>()

    @SerializedName("subcategory")
    public var subcategory: ArrayList<Subct10> = ArrayList<Subct10>()

    @SerializedName("subid")
    public var subid: String = ""

    @SerializedName("discription")
    public var discription: String = ""

    @SerializedName("createdDate")
    public var createdDate: String = ""

    @SerializedName("manufacturerCode")
    public var manufacturerCode: String = ""

    @SerializedName("categoryimage")
    public var categoryimage: ctaimG11 = ctaimG11()

    @SerializedName("active")
    public var active: String = ""


}

class ctaimG11 {

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

class Subct10 {
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

class Statedt1 {
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

