package net.jgarcia.internationalbusiness.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rates(
        val from: String,
        val to: String,
        var rate: String
    ): Parcelable{


}