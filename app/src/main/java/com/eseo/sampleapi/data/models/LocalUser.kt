package com.eseo.sampleapi.data.models

data class LocalUser(
    var id: Int,
    var email: String,
    var first_name: String,
    var last_name: String,
    var avatar: String
) {
    fun identity(): String {
        return "$first_name $last_name"
    }
}