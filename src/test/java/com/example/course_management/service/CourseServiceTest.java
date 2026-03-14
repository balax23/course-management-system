package com.example.course_management.service;

import com.example.course_management.dto.CourseRequestDto;
import com.example.course_management.dto.CourseResponseDto;
import com.example.course_management.entity.Course;
import com.example.course_management.entity.User;
import com.example.course_management.exception.ResourceNotFoundException;
import com.example.course_management.mapper.CourseMapper;
import com.example.course_management.repository.CourseRepository;
import com.example.course_management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    private User instructor;
    private Course course;
    private CourseRequestDto requestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        instructor = new User();
        instructor.setId(1L);
        instructor.setName("John Instructor");

        course = Course.builder()
                .id(1L)
                .title("Spring Boot Advanced")
                .description("Advanced backend development")
                .startDate(LocalDate.now())
                .maxParticipants(20)
                .instructor(instructor)
                .students(new HashSet<>())
                .categories(new HashSet<>())
                .build();

        requestDto = CourseRequestDto.builder()
                .title("Spring Boot Advanced")
                .description("Advanced backend development")
                .maxParticipants(20)
                .startDate(LocalDate.now())
                .instructorId(1L)
                .build();
    }

    @Test
    void testCreateCourse() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(courseMapper.toResponseDto(course)).thenReturn(
                new CourseResponseDto(
                        course.getId(),
                        course.getTitle(),
                        course.getDescription(),
                        instructor.getName(),
                        course.getStudents().size(),
                        new HashSet<>()
                )
        );

        CourseResponseDto response = courseService.createCourse(requestDto);

        assertNotNull(response);
        assertEquals("Spring Boot Advanced", response.getTitle());
        assertEquals("John Instructor", response.getInstructorName());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testCreateCourse_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            courseService.createCourse(requestDto);
        });

        verify(courseRepository, never()).save(any(Course.class));
    }
}

