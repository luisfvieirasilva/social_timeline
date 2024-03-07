package com.example.socialTimeline

import com.example.schema.generated.types.User
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class ShowsDataFetcher {
    private val users = listOf(
        User("user1", "User1"),
        User("user2", "User2"),)

    @DgsQuery
    fun users(): List<User> {
        return users
    }

    @DgsQuery
    fun user(@InputArgument username : String): User? {
        return users.find {user -> user.username == username}
    }
}