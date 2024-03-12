package com.example.socialTimeline.db.entities

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.neo4j.core.schema.*
import java.time.LocalDateTime

@Node(PostEntity.N_POST)
data class PostEntity(
    @Property(P_TITLE) val title: String,
    @Property(P_BODY) val body: String,
    @Relationship(R_POSTED, direction = Relationship.Direction.INCOMING) val postedBy: UserEntity,
    @Relationship(
        R_REACTED,
        direction = Relationship.Direction.INCOMING
    ) val reactedBy: Set<UserReactionRelationship> = emptySet(),
    @Property(P_CREATED_AT) @CreatedDate val createdAt: LocalDateTime? = null,
    @Id @GeneratedValue val id: String? = null
) {
    companion object {
        const val N_POST = "Post"
        const val P_TITLE = "title"
        const val P_BODY = "body"
        const val P_CREATED_AT = "createdAt"
        const val R_POSTED = "POSTED"
        const val R_REACTED = "REACTED"
    }

    fun withId(id: String): PostEntity {
        if (this.id === id) {
            return this;
        }

        return this.copy(id = id)
    }
}