package com.example.socialTimeline.entities

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property
import java.time.LocalDateTime

@Node("User")
data class UserEntity(
    @Property("username") val username: String,
    @Property("name") val name: String,
    @Property("createdAt") @CreatedDate val createdAt: LocalDateTime? = null,
    @Id @GeneratedValue val id: String? = null
) {

    fun withId(id: String): UserEntity {
        if (this.id === id) {
            return this;
        }

        return UserEntity(this.username, this.name, this.createdAt, id)
    }
}