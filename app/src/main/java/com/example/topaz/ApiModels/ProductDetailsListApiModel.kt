package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class ProductDetailsListApiModel {

    @SerializedName("productid")
    public var productid: Int = 0

    @SerializedName("productTitle")
    public var productTitle: String = ""

    @SerializedName("categoryType")
    public var categoryType: CategoryType = CategoryType()

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

    @SerializedName("price")
    public var price: Int = 0


    @SerializedName("stock")
    public var stock: Int = 0

    @SerializedName("threshold")
    public var threshold: Int = 0


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
    public var Productimage2: ProductImage2 = ProductImage2()

    @SerializedName("active")
    public var active: Boolean = true

    @SerializedName("sku")
    public var sku: String = ""

}

class ProductImage2 {
    public var imageId: Int = 0
    public var productid: Int = 0
    public var imagepath: String = ""
    public var imagebyte: String = ""
    public var creationTime: String = ""

}


class CategoryType {
    public var categoryid: String = ""
    public var categoryName: String = ""
    public var tags: ArrayList<Tagss> = ArrayList<Tagss>()
    public var subcategory: ArrayList<SubCategory> = ArrayList<SubCategory>()
    public var discription: String = ""
    public var createdDate: String = ""
    public var manufacturerCode: String = ""
    public var categoryimage: CategoryImage2 = CategoryImage2()
    public var active: Boolean = true


}

class CategoryImage2 {

    public var imageId: Int = 0
    public var categoryid: String = ""
    public var imagepath: String = ""
    public var imagebyte: String = ""
    public var creationTime: String = ""
}

class Tagss {
    public var tags: String = ""
}
