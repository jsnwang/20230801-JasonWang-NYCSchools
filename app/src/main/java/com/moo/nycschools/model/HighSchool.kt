package com.moo.nycschools.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HighSchool(
    @SerializedName("city")
    val city: String = "",
    @SerializedName("dbn")
    val dbn: String = "",
    @SerializedName("neighborhood")
    val neighborhood: String = "",
    @SerializedName("primary_address_line_1")
    val primaryAddressLine: String = "",
    @SerializedName("phone_number")
    val phoneNumber: String = "",
    @SerializedName("school_email")
    val schoolEmail: String = "",
    @SerializedName("school_name")
    val schoolName: String = "",
    @SerializedName("state_code")
    val stateCode: String = "",
    @SerializedName("zip")
    val zip: String = "",
    @SerializedName("location")
    val location: String = "",
    @SerializedName("overview_paragraph")
    val overviewParagraph: String = "",
) : Parcelable