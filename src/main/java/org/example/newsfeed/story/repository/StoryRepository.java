package org.example.newsfeed.story.repository;

import org.example.newsfeed.story.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {
}
