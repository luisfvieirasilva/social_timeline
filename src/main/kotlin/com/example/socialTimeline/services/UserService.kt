package com.example.socialTimeline.services

import com.example.socialTimeline.entities.UserEntity
import com.example.socialTimeline.repositories.UserRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

interface UserService {
    fun findAll(): List<UserEntity>
    fun findByUsername(username: String): UserEntity?
}

@Service
class UserServiceImpl(val userRepository: UserRepository) : UserService {
    override fun findAll(): List<UserEntity> {
        return userRepository.findAll(Sort.unsorted()).toList()
    }

    override fun findByUsername(username: String): UserEntity? {
        return userRepository.findByUsername(username)
    }
}