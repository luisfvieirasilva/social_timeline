package com.example.socialTimeline.converters

import com.example.schema.generated.types.PostReactionType
import com.example.socialTimeline.db.entities.ReactionType

fun PostReactionType.toEntity(): ReactionType {
    return when (this) {
        PostReactionType.LIKED -> ReactionType.LIKED
        PostReactionType.UNLIKED -> ReactionType.UNLIKED
    }
}