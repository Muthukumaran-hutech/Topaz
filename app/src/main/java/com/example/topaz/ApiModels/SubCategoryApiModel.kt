package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class SubCategoryApiModel {

    @SerializedName("productid")
    public var productid: Int = 0

    @SerializedName("productTitle")
    public var productTitle: String = ""

    @SerializedName("categoryType")
    public var categoryType: SubCategoryType = SubCategoryType()

    @SerializedName("subcategory")
    public var subcategory: mainSubCategory = mainSubCategory()

    @SerializedName("minOrderQty")
    public var minOrderQty: Int = 0

    @SerializedName("maxOrderQty")
    public var maxOrderQty: Int = 0

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
    public var stock: Int = 0

    @SerializedName("threshold")
    public var threshold: Long = 0

    @SerializedName("collection")
    public var collection: String = ""

    @SerializedName("currency")
    public var currency: String = ""



    @SerializedName("discription")
    public var discription: String = ""

    @SerializedName("tag")
    public var tag: String = ""

    @SerializedName("createdDate")
    public var createdDate: String = ""

    @SerializedName("productimage")
    public var Productimage1: Productimage = Productimage()

    @SerializedName("manufacturerCode")
    public var manufacturerCode: String = ""

    @SerializedName("active")
    public var active: Boolean = true

    @SerializedName("sku")
    public var sku: String = ""


}

class Productimage {

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

class mainSubCategory {

    @SerializedName("subid")
    public var subid: Int = 0

    @SerializedName("subcategory")
    public var subcategory: String = ""

    @SerializedName("categoryid")
    public var categoryid: String = ""

    @SerializedName("categoryName")
    public var categoryName: String = ""


}

class catt {
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

class Sub {
    @SerializedName("subid")
    public var subid: Int = 0

    @SerializedName("subcategory")
    public var subcategory: String = ""

    @SerializedName("categoryid")
    public var categoryid: String = ""

    @SerializedName("categoryName")
    public var categoryName: String = ""
}

class SubCategoryType {

    @SerializedName("categoryid")
    public var categoryid: String = ""

    @SerializedName("categoryName")
    public var categoryName: String = ""

    @SerializedName("tags")
    public var tags: ArrayList<SubTags> = ArrayList<SubTags>()

    @SerializedName("subcategory")
    public var subcategory: ArrayList<Sub> = ArrayList<Sub>()

    @SerializedName("subid")
    public var subid: Int = 0

    @SerializedName("discription")
    public var discription: String = ""

    @SerializedName("createdDate")
    public var createdDate: String = ""

    @SerializedName("manufacturerCode")
    public var manufacturerCode: String = ""

    @SerializedName("categoryimage")
    public var categoryimage: catt = catt()

    @SerializedName("active")
    public var active: Boolean = true
}

class SubTags {
    public var tags: String = ""
}
