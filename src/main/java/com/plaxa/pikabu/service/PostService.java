package com.plaxa.pikabu.service;

import com.plaxa.pikabu.dto.PostCreateRequestDto;
import com.plaxa.pikabu.dto.PostReadDto;
import com.plaxa.pikabu.mapper.PostMapper;
import com.plaxa.pikabu.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;
    private final FileService fileService;

    public Optional<PostReadDto> findByTitle(String title) {
        return postRepository.findByTitle(title)
                .map(postMapper::mapPostToPostReadDto);
    }

    public List<PostReadDto> findAll() {
        return postRepository.findAll().stream()
                .map(postMapper::mapPostToPostReadDto)
                .toList();
    }

    public Optional<PostReadDto> findById(Integer id) {
        return postRepository.findById(id)
                .map(postMapper::mapPostToPostReadDto);

    }

    public List<PostReadDto> findAllByTagId(Integer id) {
        return postRepository.findAllByTagId(id).stream()
                .map(postMapper::mapPostToPostReadDto)
                .toList();
    }

    @Transactional
    public PostReadDto create(PostCreateRequestDto postCreateRequestDto) {
        return Optional.of(postCreateRequestDto)
                .map(postCreateRequestDto1 -> postMapper.mapPostCreateRequestDtoToPost(postCreateRequestDto1, userService.getCurrentUser()))
                .map(postRepository::save)
                .map(postMapper::mapPostToPostReadDto)
                .orElseThrow();
    }
}
