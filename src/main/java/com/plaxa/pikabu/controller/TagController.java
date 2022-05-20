package com.plaxa.pikabu.controller;

import com.plaxa.pikabu.dto.TagReadDto;
import com.plaxa.pikabu.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    @GetMapping("/by-post/{id}")
    public List<TagReadDto> findByPost(@PathVariable Integer id){
        return tagService.findByPost(id);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public TagReadDto create(@RequestBody TagCreateRequestDto tagCreateRequestDto){
//        return tagService.create(tagCreateRequestDto);
//    }
}
