package com.projectbyanuj.Secure_Journal_Application.journal_apis.mappers;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.TagRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.TagResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Tag;

public class TagMapper {
    public static Tag mapToEntity(TagRequest request) {
        return Tag.builder()
                .tagName(request.getTagName())
                .build();
    }

    public static TagResponse mapToResponse(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .tagName(tag.getTagName())
                .build();
    }
}
