package com.example.socialTimeline.db.entities

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.neo4j.core.schema.*
import java.time.LocalDateTime

@Node("Post")
data class PostEntity(
    @Property("title") val title: String,
    @Property("body") val body: String,
    @Relationship("POSTED", direction = Relationship.Direction.INCOMING) val postedBy: UserEntity,
    @Relationship(
        "REACTED",
        direction = Relationship.Direction.INCOMING
    ) val reactedBy: Set<UserReactionRelationship> = emptySet(),
    @Property("createdAt") @CreatedDate val createdAt: LocalDateTime? = null,
    @Id @GeneratedValue val id: String? = null
) {

    fun withId(id: String): PostEntity {
        if (this.id === id) {
            return this;
        }

        return this.copy(id = id)
    }
}