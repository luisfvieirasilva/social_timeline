package com.example.socialTimeline.repositories

import com.example.socialTimeline.entities.UserEntity
import org.springframework.data.repository.PagingAndSortingRepository

interface UserRepository : PagingAndSortingRepository<UserEntity, String> {
    fun findByUsername(username: String): UserEntity?
}