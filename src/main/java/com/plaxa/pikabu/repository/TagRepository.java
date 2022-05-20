package com.plaxa.pikabu.repository;

import com.plaxa.pikabu.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

//    @Query(value = "SELECT t " +
//            "FROM Tag t " +
//            "inner JOIN PostTag pt on pt.tag.id = t.id " +
//            "where pt.post.id = :postId ", nativeQuery = true)
//    List<TagDto> findAllByPostId(Integer postId);

    @Query(value = "SELECT * FROM tag WHERE id " +
            "IN (SELECT tag_id FROM post_tag WHERE post_id = :postId)",
            nativeQuery = true)
    List<Tag> findAllByPostId(Integer postId);
}
