package com.example.topaz.Models

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoriesModel(
    var CateegoryImage: String = "",
    var CateegoryName: String = "",
    var CateegoryId: String = "",
    var CateegorySubId: String

) : Parcelable
