package com.plaxa.pikabu.repository;

import com.plaxa.pikabu.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Optional<Post> findByTitle(String title);

    Optional<Post> findById(Integer id);

    @Query(value = "SELECT * FROM post WHERE id " +
            "IN (SELECT post_id FROM post_tag WHERE tag_id = :tagId)",
            nativeQuery = true)
    List<Post> findAllByTagId(Integer tagId);

    List<Post> findAll();
}
