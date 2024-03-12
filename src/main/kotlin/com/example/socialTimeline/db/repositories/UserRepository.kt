package com.example.socialTimeline.db.repositories

import com.example.socialTimeline.db.entities.UserEntity
import org.springframework.data.neo4j.repository.Neo4jRepository

interface UserRepository : Neo4jRepository<UserEntity, String> {
    fun findByUsername(username: String): UserEntity?
}