package com.example.socialTimeline.db.repositories

import com.example.socialTimeline.db.entities.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntity, String> {
    fun findByUsername(username: String): UserEntity?
}