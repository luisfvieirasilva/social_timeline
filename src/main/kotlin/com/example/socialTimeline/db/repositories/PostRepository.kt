package com.example.socialTimeline.db.repositories

import com.example.socialTimeline.db.entities.PostEntity
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface PostRepository : CrudRepository<PostEntity, String> {

    @Query("MATCH path=(u:User)-[:POSTED]->(p:Post) WHERE u.username = :#{#username} RETURN path")
    fun findByAuthorUsername(@Param("username") authorUsername: String): List<PostEntity>
}