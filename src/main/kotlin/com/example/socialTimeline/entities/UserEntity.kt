package com.example.socialTimeline.entities

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property
import java.time.OffsetDateTime

@Node("User")
class UserEntity(
    @Property("username") val username: String,
    @Property("name") val name: String,
    @Property("createdAt") @CreatedDate val createdAt: OffsetDateTime,
    @Id @GeneratedValue val id: String? = null
) {

    fun withId(id: String): UserEntity {
        if (this.id === id) {
            return this;
        }

        return UserEntity(this.username, this.name, this.createdAt, id)
    }
}