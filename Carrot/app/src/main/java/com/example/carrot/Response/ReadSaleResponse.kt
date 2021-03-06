package com.example.carrot.Response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ReadSaleResponse(
    @SerializedName("sales")
    var sales: ArrayList<Sale>
)

@Parcelize
data class Sale(
    @SerializedName("id")
    var id: Int = -1,

    @SerializedName("title")
    var title: String = "",

    @SerializedName("content")
    var content: String = "",

    @SerializedName("price")
    var price: Int = -1,

    @SerializedName("priceProposal")
    var priceProposal: Boolean = false,

    @SerializedName("chatCount")
    var chatCount: Int = 0,

    @SerializedName("favoriteCount")
    var favoriteCount: Int = 0,

    @SerializedName("viewCount")
    var viewCount: Int = 0,

    @SerializedName("createdAt")
    var createdAt: String = "",

    @SerializedName("Images")
    var images: ArrayList<Image> = arrayListOf()
) : Parcelable