package com.plaxa.pikabu.repository;

import com.plaxa.pikabu.entity.Comment;
import com.plaxa.pikabu.entity.Post;
import com.plaxa.pikabu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByPost(Post post);

    List<Comment> findAllByUser(User user);
}
