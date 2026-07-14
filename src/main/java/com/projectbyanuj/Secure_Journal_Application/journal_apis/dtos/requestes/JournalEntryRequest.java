package com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalEntryRequest {

        @NotBlank(message = "Journal entry title is required.")
        @Size(min = 3, max = 100,
                message = "Journal entry title must be between 3 and 100 characters.")
        private String title;

        @NotBlank(message = "Journal entry content is required.")
        @Size(min = 10, max = 3000,
                message = "Journal entry content must be between 10 and 3000 characters.")
        private String content;

        @NotNull(message = "Journal ID is required.")
        private Long journalId;

        @NotNull(message = "Category ID is required.")
        private Long categoryId;

        private Set<Long> tagIds;

}
