package com.plaxa.pikabu.service;

import com.plaxa.pikabu.dto.VoteCreateRequestDto;
import com.plaxa.pikabu.entity.Post;
import com.plaxa.pikabu.entity.Vote;
import com.plaxa.pikabu.exception.PikabuException;
import com.plaxa.pikabu.repository.PostRepository;
import com.plaxa.pikabu.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.plaxa.pikabu.entity.VoteType.UPVOTE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public void create(VoteCreateRequestDto voteCreateRequestDto) {
        Post post = postRepository.findById(voteCreateRequestDto.getPostId())
                .orElseThrow();
        Optional<Vote> maybeVote = voteRepository.findTopByPostAndUserOrderByIdDesc(post, userService.getCurrentUser());
        if (maybeVote.isPresent() &&
                maybeVote.get().getVoteType()
                        .equals(voteCreateRequestDto.getVoteType())) {
            throw new PikabuException("You have already " + voteCreateRequestDto.getVoteType() + "d for this post");
        }
        if (UPVOTE.equals(voteCreateRequestDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        Vote vote = voteRepository.save(mapToVote(voteCreateRequestDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteCreateRequestDto voteCreateRequestDto, Post post) {
        return Vote.builder()
                .voteType(voteCreateRequestDto.getVoteType())
                .post(post)
                .user(userService.getCurrentUser())
                .build();
    }
}
