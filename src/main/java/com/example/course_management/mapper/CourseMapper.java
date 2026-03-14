package com.example.course_management.mapper;

import com.example.course_management.dto.CourseResponseDto;
import com.example.course_management.entity.Course;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public CourseResponseDto toResponseDto(Course course) {
        return CourseResponseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .instructorName(course.getInstructor().getName())
                .enrolledStudents(course.getStudents().size())
                .categories(
                        course.getCategories()
                                .stream()
                                .map(category -> category.getName())
                                .collect(Collectors.toSet())
                )
                .build();
    }
}