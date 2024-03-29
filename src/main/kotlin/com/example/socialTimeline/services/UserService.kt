package com.example.socialTimeline.services

import com.example.socialTimeline.db.entities.UserEntity
import com.example.socialTimeline.db.repositories.UserRepository
import org.springframework.stereotype.Service

interface UserService {
    fun findAll(): List<UserEntity>
    fun findByUsername(username: String): UserEntity?
    fun createUser(username: String, name: String): UserEntity
}

@Service
class UserServiceImpl(val userRepository: UserRepository) : UserService {
    override fun findAll(): List<UserEntity> {
        return userRepository.findAll().toList()
    }

    override fun findByUsername(username: String): UserEntity? {
        return userRepository.findByUsername(username)
    }

    override fun createUser(username: String, name: String): UserEntity {
        val user = UserEntity(username, name)
        return userRepository.save(user)
    }
}