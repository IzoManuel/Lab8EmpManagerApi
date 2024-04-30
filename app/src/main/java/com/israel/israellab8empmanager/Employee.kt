package com.israel.israellab8empmanager

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.annotations.SerializedName

@JsonIgnoreProperties(ignoreUnknown = true)
data class Employee(
    @SerializedName("id_number") val idNumber: String,
    @SerializedName("username") val username: String,
    @SerializedName("salary") val salary: String,
    @SerializedName("image_url") val employeeImageUrl: String?,
    @SerializedName("department") val department: String,
) {
    // Default constructor
    constructor() : this("", "", "", null, "")
}