package com.example.course_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Integer maxParticipants;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private Long instructorId;

}