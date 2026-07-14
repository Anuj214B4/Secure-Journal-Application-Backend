package com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {
    @Size(min = 3, max = 200,
            message = "Comments must be between 3 and 200 characters.")
    private String comment;
}
