package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class CategoryListApiModel {


    @SerializedName("categoryid")
    public var categoryid: String = ""

    @SerializedName("categoryName")
    public var categoryName: String = ""

    @SerializedName("tags")
    public var tags: ArrayList<Tags> = ArrayList<Tags>()

    @SerializedName("subcategory")
    public var subcategory: ArrayList<SubCategory> = ArrayList<SubCategory>()

    @SerializedName("discription")
    public var discription: String = ""

    @SerializedName("createdDate")
    public var createdDate: String = ""


    @SerializedName("manufacturerCode")
    public var manufacturerCode: String = ""

    @SerializedName("categoryimage")
    public var categoryimage: CategoryImage = CategoryImage()

    @SerializedName("active")
    public var active: Boolean = true

}

class Tags {
    public var tags: String = ""
}

class CategoryImage {

    public var imageId : Int = 0
    public var categoryid : String = ""
    public var imagepath : String =""
    public var imagebyte : String = ""
    public var creationTime : String = ""

}


class SubCategory {

    public var subcategory : String = ""

}
