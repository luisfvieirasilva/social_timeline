package com.example.socialTimeline.graphql

import com.example.schema.generated.types.User
import com.example.schema.generated.types.UserInput
import com.example.socialTimeline.db.entities.UserEntity
import com.example.socialTimeline.services.UserService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.beans.factory.annotation.Autowired
import java.time.ZoneId

@DgsComponent
class UserFetcher {

    @Autowired
    lateinit var userService: UserService

    @DgsQuery
    fun users(): List<User> {
        return userService.findAll().map { it.toDTO() }
    }

    @DgsQuery
    fun user(@InputArgument username: String): User? {
        return userService.findByUsername(username)?.toDTO()
    }

    @DgsMutation
    fun createUser(@InputArgument user: UserInput): User {
        return userService.createUser(user.username, user.name).toDTO()
    }

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
            this.createdAt.atZone(ZoneId.systemDefault()).toOffsetDateTime()
        )
    }
}