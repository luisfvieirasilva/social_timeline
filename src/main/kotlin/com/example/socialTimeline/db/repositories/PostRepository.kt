package com.example.socialTimeline.db.repositories

import com.example.socialTimeline.db.entities.PostEntity
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface PostRepository : CrudRepository<PostEntity, String> {

    @Query("MATCH path=(u:User)-[:POSTED]->(p:Post) WHERE u.username in :#{#usernames} RETURN path")
    fun findByAuthorUsername(@Param("usernames") usernames: List<String>): List<PostEntity>

    @Query("MATCH path=(u:User)-[:POSTED]->(p:Post) WHERE elementId(u) in :#{#ids} RETURN path")
    fun findByAuthorId(@Param("ids") ids: List<String>): List<PostEntity>
}