package com.projectbyanuj.Secure_Journal_Application.journal_apis.mappers;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.JournalRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.JournalResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Journal;

public class JournalMapper {

    public static Journal mapToEntity(JournalRequest request){
        return Journal.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();

    }

    public static JournalResponse mapToResponse(Journal journal){
        return JournalResponse.builder()
                .id(journal.getId())
                .title(journal.getTitle())
                .description(journal.getDescription())
                .build();

    }
}
