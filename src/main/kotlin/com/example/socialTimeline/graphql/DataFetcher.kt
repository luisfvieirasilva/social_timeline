package com.example.socialTimeline.graphql

import com.example.schema.generated.DgsConstants
import com.example.schema.generated.types.*
import com.example.socialTimeline.converters.toDTO
import com.example.socialTimeline.converters.toEntity
import com.example.socialTimeline.graphql.dataloaders.UserPostsDataLoader
import com.example.socialTimeline.services.PostService
import com.example.socialTimeline.services.UserService
import com.netflix.graphql.dgs.*
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.CompletableFuture

@DgsComponent
class UserFetcher(val userService: UserService) {

    @DgsQuery
    fun users(): List<User> {
        return userService.findAll().map { it.toDTO() }
    }

    @DgsQuery
    fun user(@InputArgument username: String): User? {
        return userService.findByUsername(username)?.toDTO()
    }

    @DgsData(parentType = DgsConstants.USER.TYPE_NAME, field = DgsConstants.USER.Posts)
    fun userPosts(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<Post>> {
        val user = dfe.getSource<User>()

        val userPostsDataLoader = dfe.getDataLoader<String, List<Post>>(UserPostsDataLoader::class.java)

        return userPostsDataLoader.load(user.id)

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

    @DgsMutation
    fun reactToPost(@InputArgument input: ReactToPostInput): UserReactionToPost {
        val (reaction, post) = postService.reactToPost(input.username, input.postId, input.reaction.toEntity())
        return reaction.toDTO(post.toDTO())
    }
}