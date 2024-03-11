package com.example.socialTimeline.graphql

import com.example.schema.generated.DgsConstants
import com.example.schema.generated.types.Post
import com.example.schema.generated.types.PostInput
import com.example.schema.generated.types.User
import com.example.schema.generated.types.UserInput
import com.example.socialTimeline.db.entities.PostEntity
import com.example.socialTimeline.db.entities.UserEntity
import com.example.socialTimeline.services.PostService
import com.example.socialTimeline.services.UserService
import com.netflix.graphql.dgs.*
import org.springframework.beans.factory.annotation.Autowired
import java.time.ZoneId

@DgsComponent
class UserFetcher {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var postService: PostService

    @DgsQuery
    fun users(): List<User> {
        return userService.findAll().map { it.toDTO() }
    }

    @DgsQuery
    fun user(@InputArgument username: String): User? {
        return userService.findByUsername(username)?.toDTO()
    }

    @DgsData(parentType = DgsConstants.USER.TYPE_NAME, field = DgsConstants.USER.Posts)
    fun userPosts(dfe: DgsDataFetchingEnvironment): List<Post> {
        val user = dfe.getSource<User>()
        val posts = postService.findAll(user.username)
        return posts.map { it.toDTO() }
    }

    @DgsMutation
    fun createUser(@InputArgument user: UserInput): User {
        return userService.createUser(user.username, user.name).toDTO()
    }
}

@DgsComponent
class PostFetcher {

    @Autowired
    lateinit var postService: PostService

    @DgsQuery
    fun posts(@InputArgument authorUsername: String?): List<Post> {
        return postService.findAll(authorUsername).map { it.toDTO() }
    }

    @DgsMutation
    fun createPost(@InputArgument post: PostInput): Post {
        return postService.createPost(post.title, post.body, post.authorUsername).toDTO()
    }
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
        this.createdAt.atZone(ZoneId.systemDefault()).toOffsetDateTime(),
        emptyList()
    )
}

private fun PostEntity.toDTO(): Post {
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