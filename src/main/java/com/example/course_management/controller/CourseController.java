package com.example.course_management.controller;

import com.example.course_management.dto.CourseRequestDto;
import com.example.course_management.dto.CourseResponseDto;
import com.example.course_management.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    public ResponseEntity<CourseResponseDto> createCourse(
            @RequestBody CourseRequestDto dto) {

        return ResponseEntity.ok(courseService.createCourse(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/enroll/{studentId}")
    public ResponseEntity<CourseResponseDto> enrollStudent(
            @PathVariable Long courseId,
            @PathVariable Long studentId){

        return ResponseEntity.ok(
                courseService.enrollStudent(courseId, studentId)
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> updateCourse(
            @PathVariable Long id,
            @RequestBody CourseRequestDto dto){

        return ResponseEntity.ok(
                courseService.updateCourse(id, dto)
        );
    }

    @PostMapping("/{courseId}/categories/{categoryId}")
    public ResponseEntity<CourseResponseDto> addCategoryToCourse(
            @PathVariable Long courseId,
            @PathVariable Long categoryId){

        return ResponseEntity.ok(
                courseService.addCategoryToCourse(courseId, categoryId)
        );
    }
}