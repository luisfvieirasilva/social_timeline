package com.example.socialTimeline.graphql.dataloaders

import com.example.schema.generated.types.Post
import com.example.socialTimeline.converters.toDTO
import com.example.socialTimeline.db.repositories.PostRepository
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.BatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage


@DgsDataLoader(name = "userPosts")
class UserPostsDataLoader(private val postRepository: PostRepository) : BatchLoader<String, List<Post>> {
    override fun load(ids: List<String>?): CompletionStage<List<List<Post>>> {
        if (ids == null) {
            return CompletableFuture.completedFuture(emptyList())
        }

        val allPostEntities = postRepository.findByAuthorId(ids)
        val postsByUsers = mutableMapOf<String, MutableList<Post>>()

        for (post in allPostEntities) {
            if (post.postedBy.id == null) {
                continue
            }
            if (postsByUsers[post.postedBy.id] == null) {
                postsByUsers[post.postedBy.id] = mutableListOf()
            }
            postsByUsers[post.postedBy.id]?.add(post.toDTO())
        }

        val orderedPosts = MutableList<MutableList<Post>>(ids.size) { _ -> mutableListOf() }
        ids.forEachIndexed { index, id -> orderedPosts[index] = postsByUsers[id] ?: mutableListOf() }

        return CompletableFuture.completedFuture(orderedPosts)
    }
}