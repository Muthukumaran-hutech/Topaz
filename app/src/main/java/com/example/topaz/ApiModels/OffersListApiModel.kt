package com.example.topaz.ApiModels

import com.google.gson.annotations.SerializedName

class OffersListApiModel {

    @SerializedName("offerId")
    public var offerId: Int = 0

    @SerializedName("title")
    public var title: String = ""

    @SerializedName("description")
    public var description: String = ""

    @SerializedName("startDate")
    public var startDate: String = ""

    @SerializedName("endDate")
    public var endDate: String = ""

    @SerializedName("offerImage")
    public var offerImage: OfferImage = OfferImage()

}

class OfferImage {

    @SerializedName("imageId")
    public var imageId: Int = 0

    @SerializedName("offerId")
    public var offerId: Int = 0

    @SerializedName("imagepath")
    public var imagepath: String = ""

    @SerializedName("creationTime")
    public var creationTime: String = ""

    @SerializedName("imagebyte")
    public var imagebyte: String = ""

}
