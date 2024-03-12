package com.example.socialTimeline.db.entities

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property
import java.time.LocalDateTime

@Node(UserEntity.N_USER)
data class UserEntity(
    @Property(P_USERNAME) val username: String,
    @Property(P_NAME) val name: String,
    @Property(P_CREATED_AT) @CreatedDate val createdAt: LocalDateTime? = null,
    @Id @GeneratedValue val id: String? = null
) {
    companion object {
        const val N_USER = "User"
        const val P_USERNAME = "username"
        const val P_NAME = "name"
        const val P_CREATED_AT = "createdAt"
    }

    fun withId(id: String): UserEntity {
        if (this.id === id) {
            return this;
        }
        return this.copy(id = id)
    }
}