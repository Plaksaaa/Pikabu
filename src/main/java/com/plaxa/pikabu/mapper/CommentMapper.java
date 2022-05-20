package com.plaxa.pikabu.mapper;

import com.plaxa.pikabu.dto.CommentCreateRequestDto;
import com.plaxa.pikabu.dto.CommentReadDto;
import com.plaxa.pikabu.entity.Comment;
import com.plaxa.pikabu.entity.Post;
import com.plaxa.pikabu.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "message", source = "commentCreateRequestDto.message")
    Comment mapCommentCreateRequestDtoToComment(CommentCreateRequestDto commentCreateRequestDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentReadDto mapCommentToCommentReadDto(Comment comment);
}
