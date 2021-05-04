package com.eseo.sampleapi.data.models.remote

data class UsersListApi (
    var page : Int,
    var perPage : Int,
    var total : Int,
    var totalPages : Int,
    var data : List<User>,
)