package com.example.course_management.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponseDto {

    private Long id;
    private String title;
    private String description;
    private String instructorName;
    private int enrolledStudents;
    private Set<String> categories;

}
