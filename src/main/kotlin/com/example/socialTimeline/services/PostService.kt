package com.example.socialTimeline.services

import com.example.socialTimeline.db.entities.PostEntity
import com.example.socialTimeline.db.repositories.PostRepository
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException
import org.springframework.stereotype.Service

interface PostService {
    fun findAll(authorUsername: String? = null): List<PostEntity>
    fun createPost(title: String, body: String, authorUsername: String): PostEntity
}

@Service
class PostServiceImpl(val postRepository: PostRepository, val userService: UserService) : PostService {

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
}