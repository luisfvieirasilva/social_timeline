package com.example.socialTimeline.db.repositories

import com.example.socialTimeline.db.entities.PostEntity
import com.example.socialTimeline.db.entities.PostEntity.Companion.N_POST
import com.example.socialTimeline.db.entities.PostEntity.Companion.R_POSTED
import com.example.socialTimeline.db.entities.UserEntity.Companion.N_USER
import com.example.socialTimeline.db.entities.UserEntity.Companion.P_USERNAME
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param

interface PostRepository : Neo4jRepository<PostEntity, String> {

    @Query("MATCH path=(u:${N_USER})-[:${R_POSTED}]->(p:${N_POST}) WHERE u.${P_USERNAME} in :#{#usernames} RETURN path")
    fun findByAuthorUsername(@Param("usernames") usernames: List<String>): List<PostEntity>

    @Query("MATCH path=(u:${N_USER})-[:${R_POSTED}]->(p:${N_POST}) WHERE elementId(u) in :#{#ids} RETURN path")
    fun findByAuthorId(@Param("ids") ids: List<String>): List<PostEntity>
}