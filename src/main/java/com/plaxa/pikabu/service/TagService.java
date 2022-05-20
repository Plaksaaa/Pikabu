package com.plaxa.pikabu.service;

import com.plaxa.pikabu.dto.TagReadDto;
import com.plaxa.pikabu.mapper.TagMapper;
import com.plaxa.pikabu.repository.PostRepository;
import com.plaxa.pikabu.repository.PostTagRepository;
import com.plaxa.pikabu.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;

    public List<TagReadDto> findByPost(Integer id) {
        return tagRepository.findAllByPostId(id).stream()
                .map(tagMapper::mapTagToTagReadDto)
                .toList();
    }

//    public TagReadDto create(TagCreateRequestDto tagCreateRequestDto) {
//        Post post = postRepository.findById(tagCreateRequestDto.getPostId())
//                .orElseThrow(() -> new PikabuException("Failed to find post"));
//
//        return Optional.of(tagCreateRequestDto)
//                .map(tagMapper::mapTagCreateRequestDtoToTag)
//                .map(tagRepository::save)
//                .map(tagMapper::mapTagToTagReadDto)
//                .orElseThrow(() -> new PikabuException("Failed to save tag"));
//
//        tagRepository.findById(tagCreateRequestDto.getId())
//                .orElseThrow(() -> new PikabuException("Failed to find post"));
//        PostTag postTag = new PostTag(,tagCreateRequestDto.getId(), post);
//        postTagRepository.save(postTag);
//    }
}
