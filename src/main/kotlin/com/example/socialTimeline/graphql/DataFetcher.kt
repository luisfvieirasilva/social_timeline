package com.example.socialTimeline.graphql

import com.example.schema.generated.types.User
import com.example.socialTimeline.entities.UserEntity
import com.example.socialTimeline.services.UserService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class DataFetcher {

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

    fun UserEntity.toDTO(): User {
        return User(this.id ?: "null", this.username, this.name, this.createdAt)
    }
}