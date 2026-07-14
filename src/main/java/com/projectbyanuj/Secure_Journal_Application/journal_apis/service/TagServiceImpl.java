package com.projectbyanuj.Secure_Journal_Application.journal_apis.service;

import com.projectbyanuj.Secure_Journal_Application.exceptipns.ResourceNotFoundException;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.TagRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.TagResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Tag;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.mappers.TagMapper;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public TagResponse createTag(TagRequest tagRequest) {
        return TagMapper.mapToResponse(
                tagRepository.save(TagMapper.mapToEntity(tagRequest))
        );
    }

    @Override
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(TagMapper::mapToResponse)
                .toList();
    }

    @Override
    public TagResponse updateTag(Long id, TagRequest tagRequest) {
        Tag existingTag = tagRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Tag not found."));
        if (existingTag.getTagName() != null) {
            existingTag.setTagName(tagRequest.getTagName());
        }
        return TagMapper.mapToResponse(tagRepository.save(existingTag));
    }

    @Override
    public boolean deleteTag(Long id) {
        return tagRepository.findById(id)
                .map(tag -> {
                    tagRepository.deleteById(id);
                    return true;
                }).orElse(false);
    }
}
