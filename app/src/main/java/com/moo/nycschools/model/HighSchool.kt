package com.moo.nycschools.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HighSchool(
    @SerializedName("bbl")
    val bbl: String,
    @SerializedName("bin")
    val bin: String,
    @SerializedName("boro")
    val boro: String,
    @SerializedName("borough")
    val borough: String,
    @SerializedName("building_code")
    val buildingCode: String,
    @SerializedName("bus")
    val bus: String,
    @SerializedName("campus_name")
    val campusName: String,
    @SerializedName("census_tract")
    val censusTract: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("dbn")
    val dbn: String,
    @SerializedName("neighborhood")
    val neighborhood: String,
    @SerializedName("primary_address_line_1")
    val primaryAddressLine: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("school_email")
    val schoolEmail: String,
    @SerializedName("school_name")
    val schoolName: String,
    @SerializedName("state_code")
    val stateCode: String,
    @SerializedName("subway")
    val subway: String,
    @SerializedName("total_students")
    val totalStudents: String,
    @SerializedName("zip")
    val zip: String,
    @SerializedName("location")
    val location: String,
)