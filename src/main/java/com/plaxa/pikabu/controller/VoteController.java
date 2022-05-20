package com.plaxa.pikabu.controller;

import com.plaxa.pikabu.dto.VoteCreateRequestDto;
import com.plaxa.pikabu.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/votes")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteCreateRequestDto voteCreateRequestDto) {
        voteService.create(voteCreateRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
