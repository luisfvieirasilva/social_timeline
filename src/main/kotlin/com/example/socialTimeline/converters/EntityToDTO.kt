package com.example.socialTimeline.converters

import com.example.schema.generated.types.Post
import com.example.schema.generated.types.PostReactionType
import com.example.schema.generated.types.User
import com.example.schema.generated.types.UserReactionToPost
import com.example.socialTimeline.db.entities.PostEntity
import com.example.socialTimeline.db.entities.ReactionType
import com.example.socialTimeline.db.entities.UserEntity
import com.example.socialTimeline.db.entities.UserReactionRelationship
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId

fun UserEntity.toDTO(): User {
    if (this.id == null) {
        throw RuntimeException("User's id is null")
    }
    if (this.createdAt == null) {
        println("User entity: $this")
        throw RuntimeException("User's created at is null")
    }

    return User(
        this.id,
        this.username,
        this.name,
        this.createdAt.toOffsetDateTime(),
        emptyList()
    )
}

fun PostEntity.toDTO(): Post {
    if (this.id == null) {
        throw RuntimeException("User's id is null")
    }
    if (this.createdAt == null) {
        throw RuntimeException("User's created at is null")
    }

    var post = Post(
        this.id,
        this.title,
        this.body,
        this.createdAt.toOffsetDateTime(),
        this.postedBy.toDTO(),
        emptyList()
    )

    post = post.copy(reactedBy = this.reactedBy.map { it.toDTO(post) })

    return post
}

fun UserReactionRelationship.toDTO(post: Post): UserReactionToPost {
    if (this.id == null) {
        throw RuntimeException("Relationship's id is null")
    }

    return UserReactionToPost(this.user.toDTO(), post, this.reactionType.toDTO())
}

fun ReactionType.toDTO(): PostReactionType {
    return when (this) {
        ReactionType.LIKED -> PostReactionType.LIKED
        ReactionType.UNLIKED -> PostReactionType.UNLIKED
    }
}

private fun LocalDateTime.toOffsetDateTime(): OffsetDateTime {
    return this.atZone(ZoneId.systemDefault()).toOffsetDateTime()
}