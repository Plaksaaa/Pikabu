package com.plaxa.pikabu.service;

import com.plaxa.pikabu.dto.CommentCreateRequestDto;
import com.plaxa.pikabu.dto.CommentReadDto;
import com.plaxa.pikabu.entity.Post;
import com.plaxa.pikabu.entity.User;
import com.plaxa.pikabu.exception.PikabuException;
import com.plaxa.pikabu.mapper.CommentMapper;
import com.plaxa.pikabu.repository.CommentRepository;
import com.plaxa.pikabu.repository.PostRepository;
import com.plaxa.pikabu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;

    public List<CommentReadDto> findAllCommentsForPost(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PikabuException::new);

        return commentRepository.findAllByPost(post).stream()
                .map(commentMapper::mapCommentToCommentReadDto)
                .toList();
    }

    @Transactional
    public CommentReadDto create(CommentCreateRequestDto commentCreateRequestDto) {
        return postRepository.findById(commentCreateRequestDto.getPostId())
                .map(post1 -> commentMapper.mapCommentCreateRequestDtoToComment(
                        commentCreateRequestDto,
                        post1,
                        userService.getCurrentUser())
                )
                .map(commentRepository::save)
                .map(commentMapper::mapCommentToCommentReadDto)
                .orElseThrow(() -> new PikabuException("post is not found"));
    }

    public List<CommentReadDto> findAllCommentsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(PikabuException::new);

        return commentRepository.findAllByUser(user).stream()
                .map(commentMapper::mapCommentToCommentReadDto)
                .toList();
    }
}
