package net.jgarcia.internationalbusiness.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
    val sku: String,
    val amount: String,
    val currency: String
): Parcelable
