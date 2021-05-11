package com.eseo.sampleapi.data.service

import com.eseo.sampleapi.data.models.LocalUser

class UsersService {

    suspend fun getUsers(page: Int): List<LocalUser> {
        return ApiService.instance.getUsers(page).data.map {
            LocalUser(it.id, it.email, it.first_name, it.last_name, it.avatar)
        }
    }

    suspend fun getUser(userId: Int): LocalUser {
        val data = ApiService.instance.getUser(userId).data
        return LocalUser(data.id, data.email, data.first_name, data.last_name, data.avatar)
    }

    companion object {
        val instance = build()

        private fun build(): UsersService {
            return UsersService()
        }
    }
}