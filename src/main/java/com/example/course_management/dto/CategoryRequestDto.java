package com.example.course_management.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestDto {

    private String name;
    private String code;
    private String description;
    private boolean active;

}
