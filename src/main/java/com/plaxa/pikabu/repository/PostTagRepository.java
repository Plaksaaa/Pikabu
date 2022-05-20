package com.plaxa.pikabu.repository;

import com.plaxa.pikabu.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Integer> {


    List<PostTag> findAllByPostId(Integer id);
}
