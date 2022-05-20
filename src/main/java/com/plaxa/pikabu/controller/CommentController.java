package com.plaxa.pikabu.controller;

import com.plaxa.pikabu.dto.CommentCreateRequestDto;
import com.plaxa.pikabu.dto.CommentReadDto;
import com.plaxa.pikabu.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/by-post/{id}")
    public ResponseEntity<List<CommentReadDto>> findAllCommentsForPost(@PathVariable Integer id) {
        return ResponseEntity.ok()
                .body(commentService.findAllCommentsForPost(id));
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody CommentCreateRequestDto commentCreateRequestDto) {
        CommentReadDto commentReadDto = commentService.create(commentCreateRequestDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentReadDto>> findAllCommentsForUser(@PathVariable String username) {
        return ResponseEntity.ok()
                .body(commentService.findAllCommentsForUser(username));
    }
}
