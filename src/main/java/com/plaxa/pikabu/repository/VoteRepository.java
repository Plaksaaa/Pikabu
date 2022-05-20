package com.plaxa.pikabu.repository;

import com.plaxa.pikabu.entity.Post;
import com.plaxa.pikabu.entity.User;
import com.plaxa.pikabu.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User currentUser);
}
