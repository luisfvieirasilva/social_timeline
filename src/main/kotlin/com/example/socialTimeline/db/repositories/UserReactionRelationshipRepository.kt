package com.example.socialTimeline.db.repositories

import com.example.socialTimeline.db.entities.UserReactionRelationship
import org.springframework.data.repository.CrudRepository

interface UserReactionRelationshipRepository : CrudRepository<UserReactionRelationship, String>