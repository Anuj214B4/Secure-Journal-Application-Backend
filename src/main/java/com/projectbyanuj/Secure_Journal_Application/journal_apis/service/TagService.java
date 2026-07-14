package com.projectbyanuj.Secure_Journal_Application.journal_apis.service;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.TagRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.TagResponse;

import java.util.List;

public interface TagService {
    TagResponse createTag(TagRequest tagRequest);

    List<TagResponse> getAllTags();

    TagResponse updateTag(Long id, TagRequest tagRequest);

    boolean deleteTag(Long id);
}
