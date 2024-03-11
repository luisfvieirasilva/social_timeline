package com.example.socialTimeline.repositories

import com.example.socialTimeline.entities.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntity, String> {
    fun findByUsername(username: String): UserEntity?
}