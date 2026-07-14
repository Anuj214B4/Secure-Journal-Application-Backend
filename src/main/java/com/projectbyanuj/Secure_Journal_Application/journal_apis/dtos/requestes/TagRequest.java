package com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagRequest {
    @NotBlank(message = "Tag name is required.")
    private String tagName;
}
