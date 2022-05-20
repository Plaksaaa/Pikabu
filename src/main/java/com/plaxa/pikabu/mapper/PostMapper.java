package com.plaxa.pikabu.mapper;

import com.plaxa.pikabu.dto.PostCreateRequestDto;
import com.plaxa.pikabu.dto.PostReadDto;
import com.plaxa.pikabu.entity.Post;
import com.plaxa.pikabu.entity.User;
import com.plaxa.pikabu.entity.Vote;
import com.plaxa.pikabu.entity.VoteType;
import com.plaxa.pikabu.repository.CommentRepository;
import com.plaxa.pikabu.repository.VoteRepository;
import com.plaxa.pikabu.service.AuthService;
import com.plaxa.pikabu.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.plaxa.pikabu.entity.VoteType.DOWNVOTE;
import static com.plaxa.pikabu.entity.VoteType.UPVOTE;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @Mapping(target = "id", source = "postCreateRequestDto.id")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postCreateRequestDto.description")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post mapPostCreateRequestDtoToPost(PostCreateRequestDto postCreateRequestDto, User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    public abstract PostReadDto mapPostToPostReadDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findAllByPost(post).size();
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByIdDesc(post,
                            userService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
