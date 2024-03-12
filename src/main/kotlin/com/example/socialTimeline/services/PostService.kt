package com.example.socialTimeline.services

import com.example.socialTimeline.db.entities.PostEntity
import com.example.socialTimeline.db.entities.ReactionType
import com.example.socialTimeline.db.entities.UserReactionRelationship
import com.example.socialTimeline.db.repositories.PostRepository
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException
import org.springframework.stereotype.Service

interface PostService {
    fun findAll(authorUsername: String? = null): List<PostEntity>
    fun createPost(title: String, body: String, authorUsername: String): PostEntity
    fun reactToPost(
        username: String,
        postId: String,
        reactionType: ReactionType
    ): Pair<UserReactionRelationship, PostEntity>
}

@Service
class PostServiceImpl(
    val postRepository: PostRepository,
    val userService: UserService
) : PostService {

    override fun findAll(authorUsername: String?): List<PostEntity> {
        if (authorUsername == null) {
            return postRepository.findAll().toList()
        }

        return postRepository.findByAuthorUsername(listOf(authorUsername))
    }

    override fun createPost(title: String, body: String, authorUsername: String): PostEntity {
        val author =
            userService.findByUsername(authorUsername) ?: throw DgsEntityNotFoundException("Username not found")

        val user = PostEntity(title, body, author)
        return postRepository.save(user)
    }

    override fun reactToPost(
        username: String,
        postId: String,
        reactionType: ReactionType
    ): Pair<UserReactionRelationship, PostEntity> {
        val user = userService.findByUsername(username) ?: throw DgsEntityNotFoundException("Username not found")
        var post =
            postRepository.findById(postId).orElseThrow { DgsEntityNotFoundException("Post not found") }

        val reactedBy = post.reactedBy.toMutableSet()
        var relationship =
            reactedBy.find { it.user.username == username }
        if (relationship != null) {
            reactedBy.remove(relationship)
            relationship = relationship.withReactionType(reactionType)
        } else {
            relationship = UserReactionRelationship(user, reactionType)
        }
        reactedBy.add(relationship)

        post = post.copy(reactedBy = reactedBy)

        post = postRepository.save(post)

        post =
            postRepository.findById(postId).orElseThrow { RuntimeException("Post not found but it should") }

        return Pair(
            post.reactedBy.find { it.user.username == username }
                ?: throw RuntimeException("Relationship not found but it should"), post
        )
    }
}