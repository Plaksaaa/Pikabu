package com.plaxa.pikabu.entity;

import com.plaxa.pikabu.exception.PikabuException;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);

    private int direction;

    VoteType(int direction) {
    }

    public static VoteType findVoteType(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.direction == direction)
                .findAny()
                .orElseThrow(PikabuException::new);
    }
}
