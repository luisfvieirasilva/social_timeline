package com.example.socialTimeline.db.entities

import org.springframework.data.neo4j.core.schema.Property
import org.springframework.data.neo4j.core.schema.RelationshipId
import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode

@RelationshipProperties
data class UserReactionRelationship(
    @TargetNode val user: UserEntity,
    @Property("reaction") val reactionType: ReactionType,
    @RelationshipId val id: String? = null,

    // TODO: Implement createdAt manually since @CreatedDate doesn't work for relationship
    //@Property("createdAt") @CreatedDate val createdAt: LocalDateTime? = null,
) {
    fun withId(id: String): UserReactionRelationship {
        if (this.id === id) {
            return this
        }

        return this.copy(id = id)
    }

    fun withReactionType(reactionType: ReactionType): UserReactionRelationship {
        if (this.reactionType == reactionType) {
            return this
        }
        return this.copy(reactionType = reactionType)
    }
}

enum class ReactionType {
    LIKED,
    UNLIKED
}