package com.plaxa.pikabu.mapper;

import com.plaxa.pikabu.dto.TagReadDto;
import com.plaxa.pikabu.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagReadDto mapTagToTagReadDto(Tag tag);

//    @Mapping(target = "postId", source = "tagCreateRequestDto.postId")
//    Tag mapTagCreateRequestDtoToTag(TagCreateRequestDto tagCreateRequestDto);
}
