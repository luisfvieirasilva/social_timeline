package com.example.socialTimeline.converters

import com.example.schema.generated.types.Post
import com.example.schema.generated.types.User
import com.example.socialTimeline.db.entities.PostEntity
import com.example.socialTimeline.db.entities.UserEntity
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
        this.createdAt.atZone(ZoneId.systemDefault()).toOffsetDateTime(),
        emptyList()
    )
}

fun PostEntity.toDTO(): Post {
    if (this.id == null) {
        throw RuntimeException("User's id is null")
    }
    if (this.createdAt == null) {
        println("User entity: $this")
        throw RuntimeException("User's created at is null")
    }

    return Post(
        this.id,
        this.title,
        this.body,
        this.createdAt.atZone(ZoneId.systemDefault()).toOffsetDateTime(),
        this.postedBy.toDTO(),
    )
}