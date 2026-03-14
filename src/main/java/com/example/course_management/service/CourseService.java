package com.example.course_management.service;

import com.example.course_management.dto.CourseRequestDto;
import com.example.course_management.dto.CourseResponseDto;
import com.example.course_management.entity.Category;
import com.example.course_management.entity.Course;
import com.example.course_management.entity.User;
import com.example.course_management.exception.ResourceNotFoundException;
import com.example.course_management.mapper.CourseMapper;
import com.example.course_management.repository.CategoryRepository;
import com.example.course_management.repository.CourseRepository;
import com.example.course_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    private final CourseMapper courseMapper = new CourseMapper();

    private static final Logger log =
            LoggerFactory.getLogger(CourseService.class);

    private final CategoryRepository categoryRepository;

    public List<CourseResponseDto> getAllCourses() {

        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponseDto)
                .toList();
    }

    public CourseResponseDto getCourseById(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Course not found with id: {}", id);
                    return new ResourceNotFoundException("Course not found");
                });

        return courseMapper.toResponseDto(course);
    }

    public CourseResponseDto createCourse(CourseRequestDto dto) {

        User instructor = userRepository.findById(dto.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

        Course course = Course.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .maxParticipants(dto.getMaxParticipants())
                .startDate(dto.getStartDate())
                .instructor(instructor)
                .students(new HashSet<>())
                .categories(new HashSet<>())
                .build();

        return courseMapper.toResponseDto(
                courseRepository.save(course)
        );
    }

    public void deleteCourse(Long id) {

        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found");
        }

        courseRepository.deleteById(id);
    }

    public CourseResponseDto enrollStudent(Long courseId, Long studentId){

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found"));

        User student = userRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        course.getStudents().add(student);

        courseRepository.save(course);

        return courseMapper.toResponseDto(course);
    }

    public CourseResponseDto updateCourse(Long id, CourseRequestDto dto){

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found"));

        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setMaxParticipants(dto.getMaxParticipants());
        course.setStartDate(dto.getStartDate());

        courseRepository.save(course);

        return courseMapper.toResponseDto(course);
    }

    public CourseResponseDto addCategoryToCourse(Long courseId, Long categoryId){

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        course.getCategories().add(category);

        courseRepository.save(course);

        return courseMapper.toResponseDto(course);
    }
}


