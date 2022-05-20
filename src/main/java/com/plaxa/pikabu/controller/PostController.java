package com.plaxa.pikabu.controller;

import com.plaxa.pikabu.dto.FileResponseDto;
import com.plaxa.pikabu.dto.PostCreateRequestDto;
import com.plaxa.pikabu.dto.PostReadDto;
import com.plaxa.pikabu.service.FileService;
import com.plaxa.pikabu.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    private final FileService fileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostReadDto create(@RequestBody PostCreateRequestDto postCreateRequestDto) {
        return postService.create(postCreateRequestDto);
    }

    @PostMapping("/pic")
    public FileResponseDto upload(@RequestParam("file") MultipartFile multipartFile) {
        return fileService.upload(multipartFile);
    }

    @PostMapping("/pic/{fileName}")
    public FileResponseDto download(@PathVariable String fileName) {
        return fileService.download(fileName);
    }

    @GetMapping("/{id}")
    public PostReadDto findById(@PathVariable Integer id) {
        return postService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-tag/{id}")
    public List<PostReadDto> findByTagId(@PathVariable Integer id){
        return postService.findAllByTagId(id);
    }
    @GetMapping
    public List<PostReadDto> findAll() {
        return postService.findAll();
    }
}
